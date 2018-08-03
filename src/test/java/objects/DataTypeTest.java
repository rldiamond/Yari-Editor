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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataTypeTest {

    @Test
    void getDisplayValue() {

        String expected;
        String result;

        //boolean
        expected = "boolean";
        result = DataType.BOOLEAN.getDisplayValue();
        assertEquals(expected, result);

        //byte
        expected = "byte";
        result = DataType.BYTE.getDisplayValue();
        assertEquals(expected, result);

        //char
        expected = "char";
        result = DataType.CHAR.getDisplayValue();
        assertEquals(expected, result);

        //double
        expected = "double";
        result = DataType.DOUBLE.getDisplayValue();
        assertEquals(expected, result);

        //float
        expected = "float";
        result = DataType.FLOAT.getDisplayValue();
        assertEquals(expected, result);

        //integer
        expected = "integer";
        result = DataType.INTEGER.getDisplayValue();
        assertEquals(expected, result);

        //long
        expected = "long";
        result = DataType.LONG.getDisplayValue();
        assertEquals(expected, result);

        //short
        expected = "short";
        result = DataType.SHORT.getDisplayValue();
        assertEquals(expected, result);

        //string
        expected = "string";
        result = DataType.STRING.getDisplayValue();
        assertEquals(expected, result);

    }

    @Test
    void getJavaCodeCompatibleValue() {

        String expected;
        String result;

        //boolean
        expected = "boolean";
        result = DataType.BOOLEAN.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //byte
        expected = "byte";
        result = DataType.BYTE.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //char
        expected = "char";
        result = DataType.CHAR.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //double
        expected = "double";
        result = DataType.DOUBLE.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //float
        expected = "float";
        result = DataType.FLOAT.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //integer
        expected = "int";
        result = DataType.INTEGER.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //long
        expected = "long";
        result = DataType.LONG.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //short
        expected = "short";
        result = DataType.SHORT.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

        //string
        expected = "String";
        result = DataType.STRING.getJavaCodeCompatibleValue();
        assertEquals(expected, result);

    }

    @Test
    void getFXCompatibleList() {
        final List<String> expectedValues = new ArrayList<>();
        Collections.addAll(expectedValues, "boolean", "byte", "char", "double", "float", "integer", "long", "short", "string");

        final List<String> resultValues = DataType.getFXCompatibleList();

        assertEquals(expectedValues.size(), resultValues.size());

        expectedValues.forEach(expectedValue -> assertTrue(resultValues.contains(expectedValue)));
    }

    @Test
    void getFromTableString() {

        //expect null
        String failureInput = "fail";
        DataType failureExpected = null;
        DataType failureResult = DataType.getFromTableString(failureInput);
        assertEquals(failureExpected, failureResult);

        Arrays.stream(DataType.values())
                .forEach(dataType -> {

                    String input = dataType.getDisplayValue();
                    assertNotNull(input);
                    DataType output = DataType.getFromTableString(input);
                    assertEquals(dataType, output);

                });

    }
}