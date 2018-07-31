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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum to describe the data objects the Yari decision table engine is capable of processing.
 */
public enum DataType {

    BOOLEAN("boolean", "boolean"),
    BYTE("byte", "byte"),
    CHAR("char", "char"),
    DOUBLE("double", "double"),
    FLOAT("float", "float"),
    INTEGER("integer", "int"),
    LONG("long", "long"),
    SHORT("short", "short"),
    STRING("string", "String");

    private final String displayValue;
    private final String javaCodeCompatibleValue;

    private DataType(String displayValue, String javaCodeCompatibleValue) {
        this.displayValue = displayValue;
        this.javaCodeCompatibleValue = javaCodeCompatibleValue;
    }

    /**
     * Return the display-friendly value of the data type.
     *
     * @return the display friendly value of the data type.
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * Return the java code-friendly value of the data type.
     *
     * @return the java code-friendly value of the data type.
     */
    public String getJavaCodeCompatibleValue() {
        return javaCodeCompatibleValue;
    }

    /**
     * Return a list of strings consisting of the display value of each data type.
     *
     * @return a list of strings consisting of the display value of each data type.
     */
    public static List<String> getFXCompatibleList() {
        return Arrays.stream(values())
                .map(DataType::getDisplayValue)
                .collect(Collectors.toList());
    }

    public static DataType getFromTableString(String dataType) {
        return Arrays.stream(values())
                .filter(type -> type.getDisplayValue().equalsIgnoreCase(dataType))
                .findFirst().orElse(null);
    }
}
