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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.TableRow;
import table.DecisionTableService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MinimumRequiredDataValidatorTest {


    @Mock
    DecisionTable decisionTable;

    @Test
    public void runValidationSuccessfully() {

        TableCondition condition = new TableCondition();
        condition.setDataType("string");
        TableAction action = new TableAction();
        action.setDatatype("string");
        TableRow row = new TableRow();
        row.setValues(Collections.singletonList("Test"));
        row.setActionValues(Collections.singletonList("Test"));
        Mockito.when(decisionTable.getRawRowData()).thenReturn(Collections.singletonList(row));
        Mockito.when(decisionTable.getTableConditions()).thenReturn(Collections.singletonList(condition));
        Mockito.when(decisionTable.getTableActions()).thenReturn(Collections.singletonList(action));
        DecisionTableService.getService().setDecisionTable(decisionTable);

        MinimumRequiredDataValidator minimumRequiredDataValidator = new MinimumRequiredDataValidator(true);
        minimumRequiredDataValidator.runValidation();
        assertTrue(minimumRequiredDataValidator.isValid());

    }

    @Test
    public void runValidationFail() {
        DecisionTableService.getService().setDecisionTable(decisionTable);

        MinimumRequiredDataValidator minimumRequiredDataValidator = new MinimumRequiredDataValidator(true);
        minimumRequiredDataValidator.runValidation();
        assertTrue(!minimumRequiredDataValidator.isValid());
        assertEquals(3, minimumRequiredDataValidator.getErrors().size());

    }

}