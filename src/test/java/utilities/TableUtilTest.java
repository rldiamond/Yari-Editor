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

import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import view.RootLayoutFactory;

import static org.junit.Assert.*;

public class TableUtilTest extends ApplicationTest {

    @Before
    public void setUp() throws Exception {

        DecisionTable decisionTable = new DecisionTable();
        RootLayoutFactory.getInstance().setDecisionTable(decisionTable);

    }

    @Test
    public void updateTable() {
        DecisionTable decisionTable = RootLayoutFactory.getInstance().getDecisionTable();
        assertTrue(decisionTable.getActions().isEmpty());
        assertTrue(decisionTable.getConditions().isEmpty());
        RootLayoutFactory.getInstance().getActionsList().add(new Action());
        RootLayoutFactory.getInstance().getConditionsList().add(new Condition());
        TableUtil.updateTable();
        assertEquals(1, decisionTable.getActions().size());
        assertEquals(1, decisionTable.getConditions().size());
    }

    @Test
    public void reorderActions() {
        RootLayoutFactory.getInstance().setDecisionTable(new DecisionTable());
        DecisionTable decisionTable = RootLayoutFactory.getInstance().getDecisionTable();

        final ObservableList<Action> actionsList = RootLayoutFactory.getInstance().getActionsList();

        TableUtil.reorderActions(0, 1);
    }

    @Test
    public void reorderConditions() {
        TableUtil.reorderConditions(0, 1);

    }
}