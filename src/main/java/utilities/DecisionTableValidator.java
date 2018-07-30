/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.yari.core.TableValidator;
import org.yari.core.YariException;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import view.RootLayoutFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Performs validation on the currently active decision table. If validation fails, the validProperty is set to false,
 * and the message is populated with the exception message from Yari.
 */
public class DecisionTableValidator {

    private TableValidator tableValidator = new TableValidator();
    private ObservableList<ValidateRequest> queue = FXCollections.observableArrayList();
    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    private BooleanProperty busy = new SimpleBooleanProperty(false);
    private StringProperty message = new SimpleStringProperty("");
    private BooleanProperty enabled = new SimpleBooleanProperty(true);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static DecisionTableValidator decisionTableValidator;

    /**
     * Return the instance of the validator.
     *
     * @return the instance of the validator.
     */
    public static DecisionTableValidator getInstance() {
        if (decisionTableValidator == null) {
            decisionTableValidator = new DecisionTableValidator();
        }
        return decisionTableValidator;
    }

    private DecisionTableValidator() {
        //when a new request is added to our queue, add to the executor queue.
        queue.addListener((ListChangeListener.Change<? extends ValidateRequest> c) -> {
            c.next();
            c.getAddedSubList().forEach(request -> {
                executorService.submit(() -> {
                    busy.set(true);
                    isValid.set(request.runValidation());
                    busy.set(false);
                });
            });
        });
    }


    /**
     * Runs the supplied XML String through Yari's validation processes. Returns true if valid, false if not.
     *
     * @param xml String representation of the {@link DecisionTable} XML.
     * @return true if valid, false if invalid.
     */
    public void validateXML(String xml) throws YariException {
        tableValidator.validateXML(xml);
    }

    /**
     * Place a request for table validation to occur. Validation occurs on an async thread.
     */
    public void requestValidation() {
        if (enabled.get()) {
            queue.add(new ValidateRequest(RootLayoutFactory.getInstance().getDecisionTable()));
        }
    }

    /**
     * Immediately run a validation (not on an async thread).
     */
    public void runValidation() {
        ValidateRequest validateRequest = new ValidateRequest(RootLayoutFactory.getInstance().getDecisionTable());
        validateRequest.runValidation();
    }

    /**
     * Update the decision table object to the latest values in the observable lists.
     */
    public void updateTable() {
        List<Row> updatedRows = new ArrayList<>();
        updatedRows.addAll(RootLayoutFactory.getInstance().getRowsList());
        RootLayoutFactory.getInstance().getDecisionTable().setRows(updatedRows);

        List<Condition> updatedConditions = new ArrayList<>();
        updatedConditions.addAll(RootLayoutFactory.getInstance().getConditionsList());
        RootLayoutFactory.getInstance().getDecisionTable().setConditions(updatedConditions);

        List<Action> updatedActions = new ArrayList<>();
        updatedActions.addAll(RootLayoutFactory.getInstance().getActionsList());
        RootLayoutFactory.getInstance().getDecisionTable().setActions(updatedActions);
    }

    /**
     * Re-order actions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public void reorderActions(int draggedIndex, int dropIndex) {
        setEnabled(false);
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            String dragged = row.getResults().get(draggedIndex);
            row.getResults().remove(draggedIndex);
            row.getResults().add(dropIndex, dragged);
        }
        setEnabled(true);
    }

    /**
     * Re-order conditions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public void reorderConditions(int draggedIndex /*from*/, int dropIndex /*to*/) {
        setEnabled(false);
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            String dragged = row.getValues().get(draggedIndex);
            row.getValues().remove(draggedIndex);
            row.getValues().add(dropIndex, dragged);
        }
        setEnabled(true);
    }

    /**
     * The valid property for the validator.
     *
     * @return changes to true if valid, false if invalid.
     */
    public BooleanProperty validProperty() {
        return isValid;
    }

    /**
     * The busy indicator for the validator.
     *
     * @return changes to true if busy, false if resting.
     */
    public BooleanProperty busyProperty() {
        return busy;
    }

    /**
     * Return the invalid message.
     *
     * @return the invalid message.
     */
    public String getMessage() {
        return message.get();
    }

    /**
     * Return the invalid message property.
     *
     * @return the invalid message property.
     */
    public StringProperty messageProperty() {
        return message;
    }

    /**
     * Enable or disable validation.
     *
     * @param enabled true to enable, false to stop.
     */
    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    /**
     * Inner-class to describe a validation request.
     */
    private class ValidateRequest {

        private final DecisionTable decisionTable;

        ValidateRequest(DecisionTable decisionTable) {
            this.decisionTable = decisionTable;
        }

        boolean runValidation() {
            boolean valid = true;
            try {
                updateTable();
                TableValidator.validateRule(decisionTable);
                decisionTable.convertRowData();
                if (decisionTable.getTableName() == null || decisionTable.getTableName().equalsIgnoreCase("")) {
                    message.set("The decision table must have a table name!");
                    return false;
                }
                if (decisionTable.getTableDescription() == null || decisionTable.getTableDescription().equalsIgnoreCase("")) {
                    message.set("The decision table must have a table description!");
                    return false;
                }
            } catch (Exception ex) {
                message.set(ex.getMessage());
                valid = false;
            }
            FileUtil.setDirty(true);
            return valid;
        }
    }
}