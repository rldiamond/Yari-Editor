/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package table;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.TableRow;
import utilities.SettingsUtil;
import validation.ValidationService;
import validation.ValidationType;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionTableService.class);
    private final ValidationService validationService = ValidationService.getService();

    private ObjectProperty<DecisionTable> decisionTable = new SimpleObjectProperty<>(null);

    //fx
    private final ObservableList<TableRow> rows = FXCollections.observableArrayList();
    private final ObservableList<TableAction> actions = FXCollections.observableArrayList();
    private final ObservableList<TableCondition> conditions = FXCollections.observableArrayList();
    private final StringProperty ruleName = new SimpleStringProperty("MyTableRule");
    private final BooleanProperty enabled = new SimpleBooleanProperty(true);

    /**
     * Private constructor.
     */
    private DecisionTableService() {
        //update listeners - always keep the decision table in sync with FX lists
        rows.addListener((ListChangeListener.Change<? extends TableRow> c) -> {
            c.next();
            updateRows();
            validationService.requestValidation();
        });
        conditions.addListener((ListChangeListener.Change<? extends TableCondition> c) -> {
            c.next();
            updateConditions();
            validationService.requestValidation();
        });
        actions.addListener((ListChangeListener.Change<? extends TableAction> c) -> {
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
     * Perform a table update, setting {@link TableRow}s, {@link TableCondition}s, and {@link TableAction}s in the table
     * equal to the FX lists.
     * <p>
     */
    public void updateTable() {
        updateRows();
        updateActions();
        updateConditions();
    }

    private void renumberRows() {
        int rowNumber = 0;
        for (TableRow row : rows) {
            row.setRowNumber(rowNumber++);
        }
    }

    private void updateRows() {
        if (!isEnabled()) {
            LOGGER.debug("The decision table service is disabled. Not updating rows.");
            return;
        }

        if (decisionTable.getValue() == null) {
            LOGGER.warn("Could not update rows as the decision table was null.");
            return;
        }

        renumberRows();
        decisionTable.getValue().setTableRows(new ArrayList<>(rows));
    }

    private void updateActions() {
        if (!isEnabled()) {
            LOGGER.debug("The decision table service is disabled. Not updating actions.");
            return;
        }

        if (decisionTable.getValue() == null) {
            LOGGER.warn("Could not update actions as the decision table was null.");
            return;
        }

        decisionTable.getValue().setTableActions(new ArrayList<>(actions));

    }

    private void updateConditions() {
        if (!isEnabled()) {
            LOGGER.debug("The decision table service is disabled. Not updating conditions.");
            return;
        }

        if (decisionTable.getValue() == null) {
            LOGGER.warn("Could not update conditions as the decision table was null.");
            return;
        }

        decisionTable.getValue().setTableConditions(new ArrayList<>(conditions));
    }

    public void updateFXListsFromTable() {
        setEnabled(false);
        //load data into our own lists
        for (TableCondition condition : decisionTable.getValue().getTableConditions()) {
            getConditions().add(condition);
        }
        for (TableAction action : decisionTable.getValue().getTableActions()) {
            getActions().add(action);
        }
        int rowNumber = 0;
        for (TableRow row : decisionTable.getValue().getRawRowData()) {
            row.setRowNumber(rowNumber++);
            getRows().add(row);
        }
        setEnabled(true);
    }

    public void clearData() {
        setEnabled(false);
        validationService.setEnabled(false);
        decisionTable.set(null);
        conditions.clear();
        actions.clear();
        rows.clear();
        boolean enabled = true;
        if (SettingsUtil.getSettings() != null) {
            enabled = !SettingsUtil.getSettings().getValidationType().equals(ValidationType.DISABLED);
        }
        validationService.setEnabled(enabled);
        setEnabled(true);
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

    public ObservableList<TableRow> getRows() {
        return rows;
    }

    public ObservableList<TableAction> getActions() {
        return actions;
    }

    public ObservableList<TableCondition> getConditions() {
        return conditions;
    }
}
