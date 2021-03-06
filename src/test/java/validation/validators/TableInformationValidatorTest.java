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
import org.yari.core.table.DecisionTable;
import utilities.DecisionTableService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TableInformationValidatorTest {

    @Mock
    DecisionTable decisionTable;

    @Test
    public void runValidationSuccessfully() {
        String description = "Table description";
        String name = "Table name";

        Mockito.when(decisionTable.getTableName()).thenReturn(name);
        Mockito.when(decisionTable.getTableDescription()).thenReturn(description);

        DecisionTableService.getService().setDecisionTable(decisionTable);

        TableInformationValidator tableInformationValidator = new TableInformationValidator(true);
        tableInformationValidator.runValidation();

        assertTrue(tableInformationValidator.isValid());

    }

    @Test
    public void runValidationFailed() {
        String description = "";
        String name = "";

        Mockito.when(decisionTable.getTableName()).thenReturn(name);
        Mockito.when(decisionTable.getTableDescription()).thenReturn(description);

        DecisionTableService.getService().setDecisionTable(decisionTable);

        TableInformationValidator tableInformationValidator = new TableInformationValidator(true);
        tableInformationValidator.runValidation();

        assertTrue(!tableInformationValidator.isValid());
        assertEquals(2,tableInformationValidator.getErrors().size());

    }

}