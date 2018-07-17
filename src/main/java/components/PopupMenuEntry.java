package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import utilities.KeyboardShortcut;

public class PopupMenuEntry extends HBox {

    public PopupMenuEntry(String title, KeyboardShortcut keyboardShortcut) {
        setAlignment(Pos.CENTER_LEFT);
        final Label titleLabel = new Label(title);
        getChildren().setAll(titleLabel);
        if (keyboardShortcut != null) {
            final Label shortCutLabel = new Label(keyboardShortcut.getLabel());
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
