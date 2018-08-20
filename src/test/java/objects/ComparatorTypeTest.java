/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package objects;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComparatorTypeTest {

    @Test
    public void getDisplayValue() {

        String expected;
        String result;

        //equal
        expected = "==";
        result = ComparatorType.EQUAL.getTableValue();
        assertEquals(expected, result);

        //not equal
        expected = "!=";
        result = ComparatorType.NOT_EQUAL.getTableValue();
        assertEquals(expected, result);

        //greater than
        expected = "GT";
        result = ComparatorType.GREATER_THAN.getTableValue();
        assertEquals(expected, result);

        //greater than equal to
        expected = "GE";
        result = ComparatorType.GREATER_THAN_EQUAL.getTableValue();
        assertEquals(expected, result);

        //less than
        expected = "LT";
        result = ComparatorType.LESS_THAN.getTableValue();
        assertEquals(expected, result);

        //less than equal to
        expected = "LE";
        result = ComparatorType.LESS_THAN_EQUAL.getTableValue();
        assertEquals(expected, result);

    }

    @Test
    public void getFXCompatibleList() {

        final List<String> expectedValues = new ArrayList<>();
        Collections.addAll(expectedValues, "==", "!=", "GT", "GE", "LT", "LE");

        final List<String> result = ComparatorType.getFXCompatibleList();

        assertEquals(expectedValues.size(), result.size());

        expectedValues.forEach(expectedValue -> assertTrue(result.contains(expectedValue)));

    }
}