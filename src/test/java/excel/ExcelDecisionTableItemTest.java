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

import objects.ComparatorType;
import objects.DataType;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class ExcelDecisionTableItemTest extends ApplicationTest {

    private ExcelDecisionTableItem item;

    @Before
    public void setUp() throws Exception {
        item = new ExcelDecisionTableItem();
        item.setComparatorType(ComparatorType.EQUAL);
        item.setDataType(DataType.STRING);
        item.setType(ExcelDecisionTableItem.Type.ACTION);
        item.setMethodName("Test");
        item.setName("Test");
    }

    @Test
    public void getType() {
        assertEquals(ExcelDecisionTableItem.Type.ACTION, item.getType());
    }

    @Test
    public void setType() {
        ExcelDecisionTableItem.Type expected = ExcelDecisionTableItem.Type.CONDITION;
        item.setType(expected);
        assertEquals(expected, item.getType());
    }

    @Test
    public void getDataType() {
        assertEquals(DataType.STRING, item.getDataType());
    }

    @Test
    public void setDataType() {
        DataType expected = DataType.BOOLEAN;
        item.setDataType(expected);
        assertEquals(expected, item.getDataType());
    }

    @Test
    public void getComparatorType() {
        assertEquals(ComparatorType.EQUAL, item.getComparatorType());
    }

    @Test
    public void setComparatorType() {
        ComparatorType expected = ComparatorType.GREATER_THAN;
        item.setComparatorType(expected);
        assertEquals(expected, item.getComparatorType());
    }

    @Test
    public void getMethodName() {
        assertEquals("Test", item.getMethodName());
    }

    @Test
    public void setMethodName() {
        String expected = "Alpha";
        item.setMethodName(expected);
        assertEquals(expected, item.getMethodName());
    }

    @Test
    public void getName() {
        assertEquals("Test", item.getName());
    }

    @Test
    public void setName() {
        String expected = "Beta";
        item.setName(expected);
        assertEquals(expected, item.getName());
    }
}