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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import utilities.SettingsUtil;
import validation.validators.TestValidator;
import validation.validators.Validator;

import java.util.ArrayList;
import java.util.List;
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
    private final ObservableList<Validation> validationQueue = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Validation latestValidation;

    private boolean isStrict;


    private ValidationService() {
        // load strict from user settings
        isStrict = SettingsUtil.getSettings().isStrictValidation();
        // while the queue has stuff, lets be busy
        busyProperty.bind(Bindings.isNotEmpty(validationQueue));
        // meanwhile, anything added to the queue is added to the executor service
        validationQueue.addListener((ListChangeListener.Change<? extends Validation> change) -> {
            change.next();
            change.getAddedSubList().forEach(validation -> {
                executorService.submit(() -> {
                    // run the validation
                    validation.run();
                    // and remove it from the queue on completion
                    if (validationQueue.contains(validation)) {
                        validationQueue.remove(validation);
                    }
                    //TODO: Do something with the validation
                    latestValidation = validation;
                    validProperty.set(validation.isValid());
                });
            });
        });

    }

    public Optional<Validation> getLatestValidation() {
        return Optional.ofNullable(latestValidation);
    }

    /**
     * Request the validation service run validation.
     */
    public void requestValidation() {
        validationQueue.add(new Validation(isStrict));
    }

    public boolean isValid() {
        return validProperty.get();
    }

    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }

    public void setEnabled(boolean enabled) {
        enabledProperty.set(enabled);
    }

    public BooleanProperty busyProperty() {
        return busyProperty;
    }

}
