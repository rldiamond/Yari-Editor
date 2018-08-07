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
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.Row;

import static org.junit.Assert.assertEquals;

public class ValidatorErrorLocationTest {

    private ValidatorErrorLocation validatorErrorLocation;
    private Action action;
    private Condition condition;
    private Row row;

    @Before
    public void setUp() {
        validatorErrorLocation = new ValidatorErrorLocation();
        action = new Action();
        action.setName("Test Action");
        condition = new Condition();
        condition.setName("Test Condition");
        row = new Row();
        row.setRowNumber(1);
        validatorErrorLocation.setAction(action);
        validatorErrorLocation.setCondition(condition);
        validatorErrorLocation.setRow(row);
        validatorErrorLocation.setRowNumber(row.getRowNumber());
        validatorErrorLocation.setColumnNumber(1);
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
        assertEquals(row, validatorErrorLocation.getRow());
    }

    @Test
    public void setRow() {
        Row row = new Row();
        row.setRowNumber(2);
        validatorErrorLocation.setRow(row);
        assertEquals(row, validatorErrorLocation.getRow());

    }

    @Test
    public void getCondition() {
        assertEquals(condition, validatorErrorLocation.getCondition());
    }

    @Test
    public void setCondition() {
        Condition condition = new Condition();
        condition.setName("Test subject beta.");
        validatorErrorLocation.setCondition(condition);
        assertEquals(condition, validatorErrorLocation.getCondition());
    }

    @Test
    public void getAction() {
        assertEquals(action, validatorErrorLocation.getAction());
    }

    @Test
    public void setAction() {
        Action action = new Action();
        action.setName("Test subject charlie.");
        validatorErrorLocation.setAction(action);
        assertEquals(action, validatorErrorLocation.getAction());
    }

    @Test
    public void getRowNumber() {
        assertEquals(1, validatorErrorLocation.getRowNumber());
    }

    @Test
    public void setRowNumber() {
        int input = 2;
        validatorErrorLocation.setRowNumber(input);
        assertEquals(input, validatorErrorLocation.getRowNumber());
    }

    @Test
    public void getColumnNumber() {
        assertEquals(1, validatorErrorLocation.getColumnNumber());
    }

    @Test
    public void setColumnNumber() {
        int input = 2;
        validatorErrorLocation.setColumnNumber(input);
        assertEquals(input, validatorErrorLocation.getColumnNumber());
    }
}