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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Simple Pane designed to appear as a material design 'card'.
 */
public class Card extends StackPane {

    private StackPane displayedContent = new StackPane();
    private StackPane bottomContent;
    private VBox cardContent = new VBox(10);

    public Card(String title) {

        //title
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px;");
        HBox titleWrapper = new HBox(titleLabel);
        titleWrapper.setAlignment(Pos.CENTER);

        //content
        cardContent = new VBox(10);
        VBox.setVgrow(displayedContent, Priority.ALWAYS);
        cardContent.getChildren().setAll(titleWrapper, displayedContent);
        getChildren().setAll(cardContent);

        //styling
        setPadding(new Insets(10, 5, 5, 5));
        DropShadow shadow = new DropShadow(7, Color.DARKGRAY);
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        setEffect(shadow);
        setStyle("-fx-background-color: ghostwhite;");
    }

    public void setDisplayedContent(Node node) {
        displayedContent.getChildren().setAll(node);
    }

    public void setBottomContent(Node node) {
        if (bottomContent == null) {
            bottomContent = new StackPane();
            bottomContent.setMaxHeight(35);
            cardContent.getChildren().add(bottomContent);
        }
        bottomContent.getChildren().setAll(node);
    }

}
