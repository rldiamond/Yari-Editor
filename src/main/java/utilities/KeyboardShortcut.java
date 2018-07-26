package utilities;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import view.RootLayoutFactory;

/**
 * Implemented KeyboardShortcuts. Adding to this enumeration is all that's needed to add an additional keyboard shortcut.
 */
public enum KeyboardShortcut {

    NEW(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), "Ctrl+N", FileUtil::newFile),
    SAVE(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), "Ctrl+S", () -> RootLayoutFactory.getInstance().save(false)),
    OPEN(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), "Ctrl+O", () -> RootLayoutFactory.getInstance().open()),
    PRINT(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN), "Ctrl+P", FileUtil::print),
    VALIDATE(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN), "Ctrl+V", () -> DecisionTableValidator.getInstance().requestValidation()),
    ADD_ROW(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN), "Ctrl+R", () -> RootLayoutFactory.getInstance().getRowsView().ifPresent(rowsView -> rowsView.addRow(null)));

    private final KeyCombination keyCombination;
    private final String displayLabel;
    private final Runnable action;

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
