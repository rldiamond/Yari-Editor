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

import objects.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.TableRow;
import utilities.DecisionTableService;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DataTypeConversionValidatorTest {

    @Mock
    DecisionTable decisionTable;

    @Test
    public void runValidationSuccessfully() {

        Arrays.stream(DataType.values()).forEach(dataType -> {

            String validString = getDataByDataType(true, dataType);
            TableCondition condition = new TableCondition();
            condition.setDataType(dataType.getDisplayValue());
            TableAction action = new TableAction();
            action.setDatatype("string");
            TableRow row = new TableRow();
            row.setValues(Collections.singletonList(validString));
            row.setActionValues(Collections.singletonList("Test"));
            Mockito.when(decisionTable.getRawRowData()).thenReturn(Collections.singletonList(row));
            Mockito.when(decisionTable.getTableConditions()).thenReturn(Collections.singletonList(condition));
            Mockito.when(decisionTable.getTableActions()).thenReturn(Collections.singletonList(action));
            DecisionTableService.getService().setDecisionTable(decisionTable);
            DataTypeConversionValidator dataTypeConversionValidator = new DataTypeConversionValidator(true);
            dataTypeConversionValidator.runValidation();
            assertTrue("Data type: " + dataType.getDisplayValue(), dataTypeConversionValidator.isValid());

        });

    }

    @Test
    public void runValidationFail() {

        Arrays.stream(DataType.values()).forEach(dataType -> {

            String validString = getDataByDataType(false, dataType);
            TableCondition condition = new TableCondition();
            condition.setDataType(dataType.getDisplayValue());
            TableAction action = new TableAction();
            action.setDatatype("string");
            TableRow row = new TableRow();
            row.setValues(Collections.singletonList(validString));
            row.setActionValues(Collections.singletonList("Test"));
            Mockito.when(decisionTable.getRawRowData()).thenReturn(Collections.singletonList(row));
            Mockito.when(decisionTable.getTableConditions()).thenReturn(Collections.singletonList(condition));
            Mockito.when(decisionTable.getTableActions()).thenReturn(Collections.singletonList(action));
            DecisionTableService.getService().setDecisionTable(decisionTable);
            DataTypeConversionValidator dataTypeConversionValidator = new DataTypeConversionValidator(true);
            dataTypeConversionValidator.runValidation();
            if (dataType.equals(DataType.STRING)) {
                assertTrue(dataTypeConversionValidator.isValid());
            } else {
                assertTrue("Data type: " + dataType.getDisplayValue(), !dataTypeConversionValidator.isValid());
            }

        });

    }

    @Test
    public void testInvalidDataTypeFail() {
        TableCondition condition = new TableCondition();
        condition.setDataType("foo");
        TableAction action = new TableAction();
        action.setDatatype("foo");
        TableRow row = new TableRow();
        row.setValues(Collections.singletonList("Test"));
        row.setActionValues(Collections.singletonList("Test"));
        Mockito.when(decisionTable.getRawRowData()).thenReturn(Collections.singletonList(row));
        Mockito.when(decisionTable.getTableConditions()).thenReturn(Collections.singletonList(condition));
        Mockito.when(decisionTable.getTableActions()).thenReturn(Collections.singletonList(action));
        DecisionTableService.getService().setDecisionTable(decisionTable);
        DataTypeConversionValidator dataTypeConversionValidator = new DataTypeConversionValidator(true);
        dataTypeConversionValidator.runValidation();
        assertTrue(!dataTypeConversionValidator.isValid());
    }

    private String getDataByDataType(boolean valid, DataType dataType) {
        String data = null;
        if (valid) {
            switch (dataType) {
                case BYTE:
                    data = "127";
                    break;
                case CHAR:
                    data = "A";
                    break;
                case LONG:
                    data = "123";
                    break;
                case FLOAT:
                    data = "1234";
                    break;
                case SHORT:
                    data = "321";
                    break;
                case DOUBLE:
                    data = "3214";
                    break;
                case STRING:
                    data = "hello, world";
                    break;
                case BOOLEAN:
                    data = "false";
                    break;
                case INTEGER:
                    data = "1";
                    break;
            }
        } else {
            switch (dataType) {
                case BYTE:
                    data = "45677";
                    break;
                case CHAR:
                    data = "8p";
                    break;
                case LONG:
                    data = "ABC";
                    break;
                case FLOAT:
                    data = "LOL";
                    break;
                case SHORT:
                    data = "MEOW";
                    break;
                case DOUBLE:
                    data = "RUFF";
                    break;
                case STRING:
                    data = "anything can be a string!";
                    break;
                case BOOLEAN:
                    data = "lol nope";
                    break;
                case INTEGER:
                    data = "YIP YIP";
                    break;
            }
        }
        return data;
    }

}
