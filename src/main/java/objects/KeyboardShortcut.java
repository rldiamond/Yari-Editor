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

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import table.DecisionTableService;
import utilities.FileUtil;
import validation.ValidationService;
import view.RootLayoutFactory;
import view.editors.DataEditor;

/**
 * Implemented KeyboardShortcuts. Adding to this enumeration is all that's needed to add an additional keyboard shortcut.
 */
public enum KeyboardShortcut {

    NEW(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), "Ctrl+N", FileUtil::newFile),
    SAVE(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), "Ctrl+S", () -> RootLayoutFactory.getInstance().save(false)),
    OPEN(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), "Ctrl+O", () -> RootLayoutFactory.getInstance().open()),
    PRINT(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN), "Ctrl+P", () -> FileUtil.print(DecisionTableService.getService().getDecisionTable())),
    VALIDATE(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN), "Ctrl+V", () -> ValidationService.getService().requestValidation()),
    ADD_ROW(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN), "Ctrl+R", () -> RootLayoutFactory.getInstance().getDataEditor().ifPresent(DataEditor::addNewRow)),
    DELETE_ROW(new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN), "Ctrl+Del", () -> RootLayoutFactory.getInstance().getDataEditor().ifPresent(DataEditor::removeSelectedRow)),
    MOVE_ROW_UP(new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN), "Ctrl+UP", () -> RootLayoutFactory.getInstance().getDataEditor().ifPresent(DataEditor::moveRowUp)),
    MOVE_ROW_DOWN(new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN), "Ctrl+DOWN", () -> RootLayoutFactory.getInstance().getDataEditor().ifPresent(DataEditor::moveRowDown));

    private final KeyCombination keyCombination;
    private final String displayLabel;
    private Runnable action;

    /**
     * KeyboardShortcut with its associated key combination, display label, and action.
     *
     * @param keyCombination the combination of keys that must be pressed to trigger the action.
     * @param displayLabel   the label displayed in the UI (key combination).
     * @param action         the action to runShortcutAction when the keyboard shortcut is pressed.
     */
    KeyboardShortcut(KeyCombination keyCombination, String displayLabel, Runnable action) {
        this.keyCombination = keyCombination;
        this.displayLabel = displayLabel;
        this.action = action;
    }


    /**
     * Run the shortcut action.
     */
    public void runShortcutAction() {
        action.run();
    }

    /**
     * Returns the key combination of the shortcut.
     *
     * @return the key combination of the shortcut.
     */
    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    /**
     * Returns a user display friendly label describing the shortcut's key combination.
     *
     * @return a user display friendly label describing the shortcut's key combination.
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

}
