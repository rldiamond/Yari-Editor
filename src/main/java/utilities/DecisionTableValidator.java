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
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import view.RootLayoutFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs validation on the currently active decision table. If validation fails, the validProperty is set to false.
 */
public class DecisionTableValidator {

    private static TableValidator tableValidator = new TableValidator();
    private static ObservableList<ValidateRequest> queue = FXCollections.observableArrayList();
    private static BooleanProperty isValid = new SimpleBooleanProperty(true);
    private static BooleanProperty busy = new SimpleBooleanProperty(false);
    private static StringProperty message = new SimpleStringProperty("");

    static {
        queue.addListener((ListChangeListener.Change<? extends ValidateRequest> c) -> {
            busy.set(true);
            while (c.next()) {
                c.getAddedSubList().forEach(request -> {
                    FXUtil.runAsync(() -> {
                        isValid.set(request.runValidation());
                        busy.set(false);
                    });
                });
            }
        });
    }

    /**
     * Runs the supplied XML String through Yari's validation processes. Returns true if valid, false if not.
     *
     * @param xml String representation of the {@link DecisionTable} XML.
     * @return true if valid, false if invalid.
     */
    public static boolean isValidXML(String xml) {
        boolean valid = true;
        try {
            tableValidator.validateXML(xml);
        } catch (Exception ex) {
            valid = false;
        }
        return valid;
    }

    /**
     * Place a request for table validation to occur. Validation occurs on an async thread.
     *
     * @param decisionTable the DecisionTable to validate.
     */
    public static void requestValidation(DecisionTable decisionTable) {
        queue.add(new ValidateRequest(decisionTable));
    }

    private static class ValidateRequest {
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
                renumberRows();
            } catch (Exception ex) {
                message.set(ex.getMessage());
                valid = false;
            }
            return valid;
        }
    }

    public static void updateTable() {
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

    public static void renumberRows() {
        var rowNumber = 0;
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            row.setRowNumber(rowNumber++);
        }
    }


    /**
     * The valid property for the validator.
     *
     * @return changes to true if valid, false if invalid.
     */
    public static BooleanProperty validProperty() {
        return isValid;
    }

    /**
     * The busy indicator for the validator.
     *
     * @return changes to true if busy, false if resting.
     */
    public static BooleanProperty busyProperty() {
        return busy;
    }

    public static String getMessage() {
        return message.get();
    }

    public static StringProperty messageProperty() {
        return message;
    }
}