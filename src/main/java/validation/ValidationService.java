/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package validation;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.yari.core.TableValidator;
import org.yari.core.YariException;
import utilities.FileUtil;
import utilities.SettingsUtil;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages data validation. Allows for validation requests and reports.
 */
public class ValidationService {

    private static ValidationService validationService;

    /**
     * Get the single instance of the Validation Service.
     *
     * @return the single instance of the validation service.
     */
    public static ValidationService getService() {
        if (validationService == null) {
            validationService = new ValidationService();
        }
        return validationService;
    }

    private final BooleanProperty validProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty enabledProperty = new SimpleBooleanProperty(true);
    private final BooleanProperty busyProperty = new SimpleBooleanProperty(false);
    private final StringProperty quickMessageProperty = new SimpleStringProperty("");
    private final ObservableList<Validation> validationQueue = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Validation latestValidation;

    private boolean isStrict;


    /**
     * Setup the validation service.
     */
    private ValidationService() {
        // load from user settings
        isStrict = SettingsUtil.getSettings().getValidationType().equals(ValidationType.STRICT);
        enabledProperty.set(!SettingsUtil.getSettings().getValidationType().equals(ValidationType.DISABLED));
        // while the queue has stuff, lets be busy
        busyProperty.bind(Bindings.isNotEmpty(validationQueue));
        // meanwhile, anything added to the queue is added to the executor service
        validationQueue.addListener((ListChangeListener.Change<? extends Validation> change) -> {
            change.next();
            change.getAddedSubList().forEach(validation -> {
                executorService.submit(() -> {
                    runValidation(validation);
                });
            });
        });
    }

    private void runValidation(Validation validation) {
        // run the validation
        validation.run();
        // and remove it from the queue on completion
        validationQueue.remove(validation);
        latestValidation = validation;
        validProperty.set(validation.isValid());
        quickMessageProperty.set(validation.getQuickMessage());
        // we assume that the file has changed when validation has run
        FileUtil.setDirty(true);
    }

    /**
     * Runs the supplied XML String through Yari's validation process.
     *
     * @param xml String representation of the {@link org.yari.core.table.DecisionTable} XML.
     * @throws YariException if validation fails.
     */
    public void validateXML(String xml) throws YariException {
        TableValidator tableValidator = new TableValidator();
        tableValidator.validateXML(xml);
    }

    /**
     * Immediately run a validation (not on an async thread).
     */
    public void runValidationImmediately() {
        runValidation(new Validation(isStrict));
    }

    /**
     * Get the latest validation object, if available.
     *
     * @return the latest validation object.
     */
    public Optional<Validation> getLatestValidation() {
        return Optional.ofNullable(latestValidation);
    }

    /**
     * Request the validation service run validation.
     */
    public void requestValidation() {
        if (enabledProperty.get()) {
            validationQueue.add(new Validation(isStrict));
        }
    }

    /**
     * Returns true if the current workspace is valid, false if not.
     *
     * @return true if the current workspace is valid, false if not.
     */
    public boolean isValid() {
        return validProperty.get();
    }

    /**
     * JavaFX property for the current validity status.
     *
     * @return property for the current validity status.
     */
    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }

    /**
     * Allows for enabling and disabling of validation.
     *
     * @param enabled set enabled or disabled.
     */
    public void setEnabled(boolean enabled) {
        // only change if the overall service is enabled
        enabledProperty.set(enabled);
    }

    /**
     * JavaFX property informing of the busy status of the service.
     *
     * @return property informing of the busy status of the service.
     */
    public ReadOnlyBooleanProperty busyProperty() {
        return busyProperty;
    }

    /**
     * JavaFX property containing a collection of error messages.
     *
     * @return
     */
    public ReadOnlyStringProperty quickMessageProperty() {
        return quickMessageProperty;
    }


    /**
     * Set whether the service should run strict validations or not.
     *
     * @param strict true to run strict validations.
     */
    public void setStrict(boolean strict) {
        isStrict = strict;
    }

    /**
     * JavaFX property informing of the enabled status of the service.
     *
     * @return property informing of the enabled status of the service.
     */
    public ReadOnlyBooleanProperty enabledProperty() {
        return enabledProperty;
    }
}
