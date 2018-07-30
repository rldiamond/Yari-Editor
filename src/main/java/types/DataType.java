package types;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum to describe the data types the Yari decision table engine is capable of processing.
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
}
