/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import utilities.TextUtil;

/**
 * Simple extension of {@link ComboBox} allowing for {@link Enum} values to easily be populated. Supply the constructor
 * with the {@link Enum} class to use.
 * <p>
 * The method {@link EnumComboBox#setPrettyPrint(boolean)} can be utilized to turn on pretty printing of the {@link
 * Enum} values (Title Case utilizing the {@link Enum#toString()} method).
 *
 * @param <T> the {@link Enum} type.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class EnumComboBox<T extends Enum> extends ComboBox<T> {

    private final BooleanProperty prettyPrint = new SimpleBooleanProperty(false);

    /**
     * Builds a {@link ComboBox} with the provided {@link Enum} class.
     *
     * @param enumType the {@link Enum} class to populate the {@link ComboBox} with.
     */
    public EnumComboBox(Class<T> enumType) {
        super(FXCollections.observableArrayList(enumType.getEnumConstants()));

        //listen for prettyPrint changes and apply the Title StringConverter when necessary.
        prettyPrint.addListener((obs, ov, isPrettyPrint) -> {
            if (isPrettyPrint) {
                setConverter(titleCaseStringConverter());
            } else {
                setConverter(null);
            }
        });
    }

    /**
     * StringConverter utilizing the {@link Enum#toString()} method to provide a Title Case representation of the supplied
     * {@link Enum} value.
     *
     * @return a Pascal case StringConverter.
     */
    private StringConverter<T> titleCaseStringConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return TextUtil.toTitleCase(object.toString());
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        };
    }

    /**
     * Turns on or off pretty print (Title Case utilizing the {@link Enum#toString()} method) for enum values within the ComboBox.
     *
     * @param prettyPrint boolean value to toggle pretty print on or off.
     */
    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint.set(prettyPrint);
    }
}