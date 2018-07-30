package types;

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

    private ComparatorType(String displayValue) {
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
}
