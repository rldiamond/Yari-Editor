/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Pane to display in a JFXDialog.
 */
public class DialogPane extends AnchorPane {

    private final StringProperty content = new SimpleStringProperty("");

    public DialogPane(){
        //styling
        setPrefSize(450, 300);
        setPadding(new Insets(15, 15, 15, 15));
        setStyle("-fx-background-color: ghostwhite;");

        //text content
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.textProperty().bind(content);
        AnchorPane.setTopAnchor(textArea, 0D);
        AnchorPane.setLeftAnchor(textArea, 0D);
        AnchorPane.setRightAnchor(textArea, 0D);
        getChildren().setAll(textArea);
    }

    /**
     * Construct a dialog pane with a button that will dismiss the provided JFXDialog.
     *
     * @param jfxDialog the JFXDialog to dismiss.
     */
    public DialogPane(JFXDialog jfxDialog) {
        this();
        //dismiss button
        HBox buttonBar = new HBox();
        JFXButton dismiss = new JFXButton("DISMISS");
        dismiss.getStyleClass().add("button-flat-gray");
        dismiss.setOnMouseClicked(me -> jfxDialog.close());
        buttonBar.getChildren().add(dismiss);
        buttonBar.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(buttonBar, 0D);
        AnchorPane.setLeftAnchor(buttonBar, 0D);
        AnchorPane.setBottomAnchor(buttonBar, 0D);
        getChildren().add(buttonBar);

    }

    /**
     * Construct a dialog pane with a customizable button.
     * @param jfxDialog
     * @param runButtonLabel
     * @param runnable
     */
    public DialogPane(JFXDialog jfxDialog, String runButtonLabel, Runnable runnable){
        this();
        HBox buttonBar = new HBox(10);
        JFXButton runButton = new JFXButton(runButtonLabel.trim().toUpperCase());
        runButton.setOnMouseClicked(me -> runnable.run());
        runButton.getStyleClass().add("button-flat-gray");
        JFXButton continueButton = new JFXButton("CONTINUE");
        continueButton.getStyleClass().add("button-flat-gray");
        continueButton.setOnMouseClicked(me -> jfxDialog.close());
        buttonBar.getChildren().addAll(runButton, continueButton);
        getChildren().add(buttonBar);
    }

    /**
     * Returns the property of the displayed content.
     *
     * @return the text property of the displayed content.
     */
    public StringProperty contentProperty() {
        return content;
    }

}
