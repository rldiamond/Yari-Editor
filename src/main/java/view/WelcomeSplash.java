/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utilities.FXUtil;
import utilities.ThemeUtil;

import javax.tools.Tool;

/**
 * The WelcomeSplash view is displayed when the application is first launched, offering users the ability to open a project
 * or start new.
 */
public class WelcomeSplash extends AnchorPane {

    @FXML
    private Pane newIcon;

    @FXML
    private Label createNewButton;

    @FXML
    private Pane openIcon;

    @FXML
    private Label openButton;

    @FXML
    private Pane closeButton;

    @FXML
    private ImageView logo;

    @FXML
    private Pane dragBar;

    public WelcomeSplash() {
        FXUtil.intializeFXML(this);
        setId("welcomeSplash");

        //install tooltip to links
        Tooltip.install(createNewButton, new Tooltip("Create a new project"));
        Tooltip.install(openButton, new Tooltip("Open a project"));

        logo.setImage(ThemeUtil.getLogo());

        //close the program when X is clicked.
        closeButton.setOnMouseClicked(me -> Platform.exit());
        closeButton.setCursor(Cursor.HAND);

    }

    /**
     * Get this view's drag bar.
     *
     * @return the drag bar.
     */
    public Pane getDragBar() {
        return dragBar;
    }

    /**
     * Retrieve the create new button for setting events.
     *
     * @return the create new button.
     */
    public Label getCreateNewButton() {
        return createNewButton;
    }

    /**
     * Retrieve the open button for setting events.
     *
     * @return the open button.
     */
    public Label getOpenButton() {
        return openButton;
    }
}
