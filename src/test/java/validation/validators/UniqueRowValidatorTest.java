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
import org.yari.core.table.Row;
import utilities.DecisionTableService;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UniqueRowValidatorTest {

    @Mock
    DecisionTable decisionTable;

    @Test
    public void testSuccess() {
        List<String> dataA = List.of("ALPHA", "BETA", "CHARLIE");
        List<String> dataB = List.of("A", "B", "C");
        Row rowA = new Row();
        rowA.setValues(dataA);
        Row rowB = new Row();
        rowB.setValues(dataB);

        Mockito.when(decisionTable.getRawRowData()).thenReturn(List.of(rowA, rowB));
        DecisionTableService.getService().setDecisionTable(decisionTable);

        UniqueRowValidator uniqueRowValidator = new UniqueRowValidator(true);

        uniqueRowValidator.runValidation();

        assertTrue(uniqueRowValidator.isValid());
        assertEquals(0, uniqueRowValidator.getErrors().size());

    }

    @Test
    public void testFailure() {
        List<String> dataA = List.of("ALPHA", "BETA", "CHARLIE");
        Row rowA = new Row();
        rowA.setValues(dataA);
        Row rowB = new Row();
        rowB.setValues(dataA);

        Mockito.when(decisionTable.getRawRowData()).thenReturn(List.of(rowA, rowB));
        DecisionTableService.getService().setDecisionTable(decisionTable);

        UniqueRowValidator uniqueRowValidator = new UniqueRowValidator(true);

        uniqueRowValidator.runValidation();

        assertTrue(!uniqueRowValidator.isValid());
        assertEquals(2, uniqueRowValidator.getErrors().size());
    }

}