package components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Extension of JFXDialog, making it much simpler to use for our needs.
 *
 * StyleClasses:
 * - Dialog content = dialog
 */
public class Dialog extends JFXDialog {

    private final StringProperty text = new SimpleStringProperty("");

    /**
     * Construct a Dialog with basic settings. Apply the dialog to the supplied StackPane.
     *
     * @param container the StackPane the dialog should appear in.
     */
    public Dialog(StackPane container) {
        //standard properties
        setTransitionType(DialogTransition.BOTTOM);
        setDialogContainer(container);

        //build content
        AnchorPane content = new AnchorPane();
        content.setPrefSize(450, 300);
        content.getStyleClass().add("dialog");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.textProperty().bind(text);
        AnchorPane.setTopAnchor(textArea, 0D);
        AnchorPane.setLeftAnchor(textArea, 0D);
        AnchorPane.setRightAnchor(textArea, 0D);

        HBox buttonBar = new HBox();
        JFXButton dismiss = new JFXButton("DISMISS");
        dismiss.getStyleClass().add("button-flat-gray");
        dismiss.setOnMouseClicked(me -> this.close());
        buttonBar.getChildren().add(dismiss);
        buttonBar.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(buttonBar, 0D);
        AnchorPane.setLeftAnchor(buttonBar, 0D);
        AnchorPane.setBottomAnchor(buttonBar, 0D);

        content.getChildren().setAll(textArea, buttonBar);

        //add content to dialog
        setContent(content);
    }

    /**
     * Set the text of the dialog.
     *
     * @param text the displayed text.
     */
    public void setText(String text) {
        this.text.set(text);
    }

    /**
     * The text property of the dialog.
     *
     * @return the text property of the dialog.
     */
    public StringProperty textProperty() {
        return text;
    }
}
