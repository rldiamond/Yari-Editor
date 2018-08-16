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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.resizing.ResizeHelper;

public class AlertDialog extends StackPane {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty body = new SimpleStringProperty("");
    private final Stage stage;

    public enum DialogType {
        INFO, WARNING, ERROR
    }

    /**
     * Construct an error dialog that will be displayed in the supplied stage.
     *
     * @param stage the stage to display the error dialog within.
     */
    public AlertDialog(Stage stage, DialogType dialogType) {
        this.stage = stage;

        getStyleClass().add("errorDialog");
        setPrefSize(450, 300);

        stage.initStyle(StageStyle.UNDECORATED);
//        stage.initModality(Modality.APPLICATION_MODAL);

        setPadding(new Insets(0, 15, 15, 15));

        VBox contentContainer = new VBox(25);

        HBox headerContainer = new HBox();
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        ResizeHelper.addUndecoratedStageDragListener(stage, headerContainer);

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(title);
        HBox.setMargin(titleLabel, new Insets(15, 0, 0, 0));

        Pane icon = new Pane();
        icon.setPrefSize(24, 24);
        icon.setMinSize(24, 24);
        icon.setMaxSize(24, 24);
        icon.setId(dialogType.name());

        headerContainer.getChildren().setAll(icon, titleLabel);

        TextArea bodyTextArea = new TextArea();
        bodyTextArea.textProperty().bind(body);
        bodyTextArea.setEditable(false);
        bodyTextArea.setWrapText(true);
        VBox.setVgrow(bodyTextArea, Priority.ALWAYS);

        contentContainer.getChildren().setAll(headerContainer, bodyTextArea);
        super.getChildren().setAll(contentContainer);

    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setBody(String body) {
        this.body.setValue(body);
    }

    public void showAndWait() {
        //TODO
    }

}
