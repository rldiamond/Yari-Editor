/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components.dialog;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import utilities.ThemeUtil;
import utilities.resizing.ResizeHelper;

public abstract class AlertDialog extends StackPane {

    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty body = new SimpleStringProperty("");
    private final Stage stage;
    private final VBox displayedContent = new VBox(25);
    private StackPane footer;

    /**
     * Construct an error dialog that will be displayed in the supplied stage.
     */
    AlertDialog(AlertDialogType alertDialogType) {

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene alertScene = new Scene(this);
        ThemeUtil.setThemeOnScene(alertScene);
        stage.setScene(alertScene);

        getStyleClass().add("errorDialog");
        setPrefSize(450, 300);

        setPadding(new Insets(0, 15, 15, 15));

        HBox headerContainer = new HBox(15);
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        headerContainer.setPadding(new Insets(20,0,0,0));
        ResizeHelper.addUndecoratedStageDragListener(stage, headerContainer);
        headerContainer.setPrefHeight(45);

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(title);

        Pane icon = new Pane();
        icon.setPrefSize(35, 35);
        icon.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        icon.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        icon.setId(alertDialogType.getIconId());

        headerContainer.getChildren().setAll(icon, titleLabel);

        TextArea bodyTextArea = new TextArea();
        bodyTextArea.textProperty().bind(body);
        bodyTextArea.setEditable(false);
        bodyTextArea.setWrapText(true);
        VBox.setVgrow(bodyTextArea, Priority.ALWAYS);

        displayedContent.getChildren().setAll(headerContainer, bodyTextArea);
        super.getChildren().setAll(displayedContent);

    }

    AlertDialog(AlertDialogType alertDialogType, Window modalOwner) {
        this(alertDialogType);
        if (modalOwner != null) {
            stage.initOwner(modalOwner);
        }
    }

    protected StackPane getFooter() {
        if (footer == null) {
            footer = new StackPane();
            displayedContent.getChildren().add(footer);
        }
        return footer;
    }

    protected Stage getStage() {
        return stage;
    }

    /**
     * Set the title to display on the dialog.
     *
     * @param title the title to display on the dialog
     */
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    /**
     * Set the body content to display in the dialog.
     * @param body the body to display in the dialog.
     */
    public void setBody(String body) {
        this.body.setValue(body);
    }

    /**
     * Show the dialog on a new stage, and wait for the user to interact with the dialog before continuing.
     */
    public void showAndWait() {
        stage.showAndWait();
    }

    protected void show(){
        stage.show();
    }

}
