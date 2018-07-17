package utilities;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import view.RootLayoutFactory;

public enum KeyboardShortcut {

    NEW(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), "Ctrl+N", FileUtil::newFile),
    SAVE(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), "Ctrl+S", () -> RootLayoutFactory.getInstance().save(false)),
    OPEN(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), "Ctrl+O", () -> RootLayoutFactory.getInstance().open()),
    PRINT(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN), "Ctrl+P", FileUtil::print),
    VALIDATE(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN), "Ctrl+V", () -> DecisionTableValidator.getInstance().requestValidation());


    private final KeyCombination keyCombination;
    private final String shortCutLabel;
    private final Runnable action;

    KeyboardShortcut(KeyCombination keyCombination, String shortCutLabel, Runnable action) {
        this.keyCombination = keyCombination;
        this.shortCutLabel = shortCutLabel;
        this.action = action;
    }

    public void run() {
        action.run();
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    public String getLabel(){
        return shortCutLabel;
    }
}
