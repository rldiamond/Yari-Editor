package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import utilities.KeyboardShortcut;
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
