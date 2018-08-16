/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package excel;

import org.junit.Test;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;

import java.io.File;

import static org.junit.Assert.*;

public class ExcelImporterTest {

    @Test
    public void importFromExcel() {

        String path = getClass().getResource("/test_excel_table.xlsx").getPath();
        File file = new File(path);

        ExcelImporter excelImporter = new ExcelImporter();
        DecisionTable decisionTable = null;
        try {
            decisionTable = excelImporter.importFromExcel(file);
        } catch (Exception e) {
            fail("Failed with an unexpected exception!");
        }

        assertNotNull(decisionTable);
        assertEquals(3, decisionTable.getTableConditions().size());
        assertEquals(1, decisionTable.getTableActions().size());
        assertEquals(3, decisionTable.getRawRowData().size());

        //condition 0: boolean, ==, isValid, Valid
        TableCondition condition = decisionTable.getTableConditions().get(0);
        assertEquals("boolean", condition.getDataType());
        assertEquals("==", condition.getComparator());
        assertEquals("isValid", condition.getMethodName());
        assertEquals("Valid", condition.getName());

        //condition 1: string, ==, name, Name
        condition = decisionTable.getTableConditions().get(1);
        assertEquals("string", condition.getDataType());
        assertEquals("==", condition.getComparator());
        assertEquals("name", condition.getMethodName());
        assertEquals("Name", condition.getName());

        //condition 1: integer, ge, getSize, Size
        condition = decisionTable.getTableConditions().get(2);
        assertEquals("integer", condition.getDataType());
        assertEquals("GE", condition.getComparator());
        assertEquals("getSize", condition.getMethodName());
        assertEquals("Size", condition.getName());

        //action 0: double, setNumber, Number
        TableAction action = decisionTable.getTableActions().get(0);
        assertEquals("double", action.getDataType());
        assertEquals("setNumber", action.getMethodName());
        assertEquals("Number", action.getName());

    }

    @Test
    public void testImportInvalidFile() {
        String path = getClass().getResource("/test_excel_table_invalid.xlsx").getPath();
        File file = new File(path);

        ExcelImporter excelImporter = new ExcelImporter();

        boolean didThrow = false;
        try {
            excelImporter.importFromExcel(file);
        } catch (ExcelImporter.ExcelImportException e) {
            // expected
            didThrow = true;
        }

        assertTrue(didThrow);
    }

    @Test
    public void getErrorMessages() {

        String path = getClass().getResource("/test_excel_table_errors.xlsx").getPath();
        File file = new File(path);

        ExcelImporter excelImporter = new ExcelImporter();
        DecisionTable decisionTable = null;
        try {
            decisionTable = excelImporter.importFromExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed with an unexpected exception!");
        }

        assertNotNull(decisionTable);
        assertEquals(3, decisionTable.getTableConditions().size());
        assertEquals(1, decisionTable.getTableActions().size());
        assertEquals(3, decisionTable.getRawRowData().size());

        assertEquals(2, excelImporter.getErrorMessages().size());

    }
}