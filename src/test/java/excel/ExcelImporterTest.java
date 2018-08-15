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

        //TODO: assert for datatype, comparatortype, methodname, name, etc.
    }

    @Test
    public void getErrorMessages() {
    }
}