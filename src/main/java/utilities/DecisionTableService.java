/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import validation.ValidationService;

import java.util.ArrayList;

/**
 * Utility for managing the state of the decision table.
 */
public class DecisionTableService {

    //<editor-fold desc="Singleton">
    private static DecisionTableService decisionTableService;

    /**
     * Returns the current DecisionTable Service.
     *
     * @return the DecisionTableService
     */
    public static DecisionTableService getService() {
        if (decisionTableService == null) {
            decisionTableService = new DecisionTableService();
        }
        return decisionTableService;
    }
    //</editor-fold>

    private static final Logger logger = LoggerFactory.getLogger(DecisionTableService.class);
    private final ValidationService validationService;

    private ObjectProperty<DecisionTable> decisionTable = new SimpleObjectProperty<>(null);

    //fx
    private final ObservableList<Row> rows = FXCollections.observableArrayList();
    private final ObservableList<Action> actions = FXCollections.observableArrayList();
    private final ObservableList<Condition> conditions = FXCollections.observableArrayList();
    private final StringProperty ruleName = new SimpleStringProperty("MyTableRule");
    private final BooleanProperty enabled = new SimpleBooleanProperty(true);

    /**
     * Private constructor.
     */
    private DecisionTableService() {
        validationService = ValidationService.getService();

        //update listeners - always keep the decision table in sync with FX lists
        rows.addListener((ListChangeListener.Change<? extends Row> c) -> {
            c.next();
            updateRows();
            validationService.requestValidation();
        });
        conditions.addListener((ListChangeListener.Change<? extends Condition> c) -> {
            c.next();
            updateConditions();
            validationService.requestValidation();
        });
        actions.addListener((ListChangeListener.Change<? extends Action> c) -> {
            c.next();
            updateActions();
            validationService.requestValidation();
        });

    }

    /**
     * Re-order actions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public void reorderActions(int draggedIndex, int dropIndex) {
        setEnabled(false);
        rows.forEach(row -> {
            String dragged = row.getResults().get(draggedIndex);
            row.getResults().remove(draggedIndex);
            row.getResults().add(dropIndex, dragged);
        });
        setEnabled(true);
        updateTable();
    }

    /**
     * Re-order conditions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public void reorderConditions(int draggedIndex, int dropIndex) {
        setEnabled(false);
        rows.forEach(row -> {
            String dragged = row.getValues().get(draggedIndex);
            row.getValues().remove(draggedIndex);
            row.getValues().add(dropIndex, dragged);
        });
        setEnabled(true);
        updateTable();
    }

    /**
     * Perform a table update, setting {@link Row}s, {@link Condition}s, and {@link Action}s in the table
     * equal to the FX lists.
     * <p>
     * TODO: This may be unnecessary with the new listeners.
     */
    public void updateTable() {
        updateRows();
        updateActions();
        updateConditions();
    }

    private void renumberRows() {
        var rowNumber = 0;
        for (Row row : rows) {
            row.setRowNumber(rowNumber++);
        }
    }

    private void updateRows() {
        if (decisionTable.getValue() == null) {
            logger.warn("Could not update rows as the decision table was null.");
            return;
        }
        if (!isEnabled()) {
            logger.debug("The decision table service is disabled. Not updating rows.");
            return;
        }
        renumberRows();
        decisionTable.getValue().setRows(new ArrayList<>(rows));
    }

    private void updateActions() {
        if (decisionTable.getValue() == null) {
            logger.warn("Could not update actions as the decision table was null.");
            return;
        }
        if (!isEnabled()) {
            logger.debug("The decision table service is disabled. Not updating actions.");
            return;
        }
        decisionTable.getValue().setActions(new ArrayList<>(actions));

    }

    private void updateConditions() {
        if (decisionTable.getValue() == null) {
            logger.warn("Could not update conditions as the decision table was null.");
            return;
        }
        if (!isEnabled()) {
            logger.debug("The decision table service is disabled. Not updating conditions.");
            return;
        }
        decisionTable.getValue().setConditions(new ArrayList<>(conditions));
    }

    private boolean isEnabled() {
        return enabled.get();
    }

    private void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public DecisionTable getDecisionTable() {
        return decisionTable.get();
    }

    public void setDecisionTable(DecisionTable decisionTable) {
        this.decisionTable.set(decisionTable);
    }

    public String getRuleName() {
        return ruleName.get();
    }

    public void setRuleName(String ruleName) {
        this.ruleName.set(ruleName);
    }

    public ObservableList<Row> getRows() {
        return rows;
    }

    public ObservableList<Action> getActions() {
        return actions;
    }

    public ObservableList<Condition> getConditions() {
        return conditions;
    }
}
