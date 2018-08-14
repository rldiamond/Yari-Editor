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
package validation.validators;

import objects.ToolView;
import org.junit.Before;
import org.junit.Test;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.TableRow;

import static org.junit.Assert.assertEquals;

public class ValidatorErrorLocationTest {

    private ValidatorErrorLocation validatorErrorLocation;
    private TableAction action;
    private TableCondition condition;
    private TableRow row;

    @Before
    public void setUp() {
        validatorErrorLocation = new ValidatorErrorLocation();
        action = new TableAction();
        action.setName("Test Action");
        condition = new TableCondition();
        condition.setName("Test Condition");
        row = new TableRow();
        row.setRowNumber(1);
        validatorErrorLocation.setTableAction(action);
        validatorErrorLocation.setTableCondition(condition);
        validatorErrorLocation.setTableRow(row);
        validatorErrorLocation.setTableRowNumber(row.getRowNumber());
        validatorErrorLocation.setTableColumnNumber(1);
        validatorErrorLocation.setToolView(ToolView.GENERAL);
    }

    @Test
    public void getToolView() {
        assertEquals(ToolView.GENERAL, validatorErrorLocation.getToolView());
    }

    @Test
    public void setToolView() {
        validatorErrorLocation.setToolView(ToolView.ACTIONS);
        assertEquals(ToolView.ACTIONS, validatorErrorLocation.getToolView());
    }

    @Test
    public void getRow() {
        assertEquals(row, validatorErrorLocation.getTableRow());
    }

    @Test
    public void setRow() {
        TableRow tableRow = new TableRow();
        tableRow.setRowNumber(2);
        validatorErrorLocation.setTableRow(tableRow);
        assertEquals(tableRow, validatorErrorLocation.getTableRow());

    }

    @Test
    public void getCondition() {
        assertEquals(condition, validatorErrorLocation.getTableCondition());
    }

    @Test
    public void setCondition() {
        TableCondition tableCondition = new TableCondition();
        tableCondition.setName("Test subject beta.");
        validatorErrorLocation.setTableCondition(tableCondition);
        assertEquals(tableCondition, validatorErrorLocation.getTableCondition());
    }

    @Test
    public void getAction() {
        assertEquals(action, validatorErrorLocation.getAction());
    }

    @Test
    public void setAction() {
        TableAction tableAction = new TableAction();
        tableAction.setName("Test subject charlie.");
        validatorErrorLocation.setTableAction(tableAction);
        assertEquals(tableAction, validatorErrorLocation.getAction());
    }

    @Test
    public void getRowNumber() {
        assertEquals(1, validatorErrorLocation.getTableRowNumber());
    }

    @Test
    public void setRowNumber() {
        int input = 2;
        validatorErrorLocation.setTableRowNumber(input);
        assertEquals(input, validatorErrorLocation.getTableRowNumber());
    }

    @Test
    public void getColumnNumber() {
        assertEquals(1, validatorErrorLocation.getTableColumnNumber());
    }

    @Test
    public void setColumnNumber() {
        int input = 2;
        validatorErrorLocation.setTableColumnNumber(input);
        assertEquals(input, validatorErrorLocation.getTableColumnNumber());
    }
}
