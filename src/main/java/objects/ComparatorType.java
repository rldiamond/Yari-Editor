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
 * Enum to describe the comparators the Yari decision table engine is capable of.
 */
public enum ComparatorType {

    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN("GT"),
    GREATER_THAN_EQUAL("GE"),
    LESS_THAN("LT"),
    LESS_THAN_EQUAL("LE");

    private final String displayValue;

    ComparatorType(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * Return the display-friendly value of the comparator.
     *
     * @return the display friendly value of the comparator.
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * Return a list of strings consisting of the display value of each comparator type.
     *
     * @return a list of strings consisting of the display value of each comparator type.
     */
    public static List<String> getFXCompatibleList() {
        return Arrays.stream(values())
                .map(ComparatorType::getDisplayValue)
                .collect(Collectors.toList());
    }

    public static ComparatorType getFromTableString(String comparatorType) {
        return Arrays.stream(values())
                .filter(type -> type.getDisplayValue().equalsIgnoreCase(comparatorType))
                .findFirst().orElse(null);
    }
}
