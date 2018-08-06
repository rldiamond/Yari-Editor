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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import view.RootLayoutFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableUtil {

    private static final Logger logger = LoggerFactory.getLogger(TableUtil.class);

    /**
     * Private constructor to hide the implicit public one.
     */
    private TableUtil() {
    }

    /**
     * Update the decision table object to the latest values in the observable lists.
     */
    public static void updateTable() {
        DecisionTable decisionTable = RootLayoutFactory.getInstance().getDecisionTable();
        if (decisionTable == null) {
            logger.error("Cannot update table! The DecisionTable object is null!");
            return;
        }
        List<Row> updatedRows = new ArrayList<>(RootLayoutFactory.getInstance().getRowsList());
        decisionTable.setRows(updatedRows);

        List<Condition> updatedConditions = new ArrayList<>(RootLayoutFactory.getInstance().getConditionsList());
        decisionTable.setConditions(updatedConditions);

        List<Action> updatedActions = new ArrayList<>(RootLayoutFactory.getInstance().getActionsList());
        decisionTable.setActions(updatedActions);
    }


    /**
     * Re-order actions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public static void reorderActions(int draggedIndex, int dropIndex) {
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            String dragged = row.getResults().get(draggedIndex);
            row.getResults().remove(draggedIndex);
            row.getResults().add(dropIndex, dragged);
        }
    }

    /**
     * Re-order conditions after drag-and-drop.
     *
     * @param draggedIndex index dragged from.
     * @param dropIndex    index dropped to.
     */
    public static void reorderConditions(int draggedIndex /*from*/, int dropIndex /*to*/) {
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            String dragged = row.getValues().get(draggedIndex);
            row.getValues().remove(draggedIndex);
            row.getValues().add(dropIndex, dragged);
        }
    }
}
