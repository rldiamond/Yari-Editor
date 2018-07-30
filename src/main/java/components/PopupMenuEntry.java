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

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import types.KeyboardShortcut;
import utilities.TextUtil;

/**
 * Simple extension of an HBox to easily create new menu entries.
 */
public class PopupMenuEntry extends HBox {

    /**
     * Create a new popup menu entry with the supplied title.
     *
     * @param title            the displayed title of the menu entry.
     */
    public PopupMenuEntry(String title) {
        setAlignment(Pos.CENTER_LEFT);
        final Label titleLabel = new Label(TextUtil.toTitleCase(title));
        getChildren().add(titleLabel);
    }

    /**
     * Create a new popup menu entry with the supplied title and keyboard shortcut.
     *
     * @param title            the displayed title of the menu entry.
     * @param keyboardShortcut the keyboard shortcut of the menu entry.
     */
    public PopupMenuEntry(String title, KeyboardShortcut keyboardShortcut) {
        this(title);
        if (keyboardShortcut != null) {
            final Label shortCutLabel = new Label(keyboardShortcut.getDisplayLabel());
            shortCutLabel.setStyle("-fx-font-size: 11px");
            shortCutLabel.setAlignment(Pos.CENTER_RIGHT);
            shortCutLabel.setTextAlignment(TextAlignment.RIGHT);
            //wrap shortcut so it can be pushed to right
            StackPane shortCutWrapper = new StackPane(shortCutLabel);
            shortCutWrapper.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(shortCutWrapper, Priority.ALWAYS);
            getChildren().add(shortCutWrapper);
        }
    }
}
