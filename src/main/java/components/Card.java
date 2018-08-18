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
 * <p>
 * StyleClasses:
 * - Card = card
 * - Card title = title
 * - Card footer = footer
 */
public class Card extends StackPane {

    private final StackPane displayedContent = new StackPane();
    private final VBox cardContent = new VBox(10);
    private StackPane footerContent;
    private HBox header;

    /**
     * Construct a card with no title.
     */
    public Card() {
        //content
        VBox.setVgrow(displayedContent, Priority.ALWAYS);
        cardContent.getChildren().setAll(displayedContent);
        getChildren().setAll(cardContent);

        //styling
        setPadding(new Insets(10, 5, 5, 5));
        DropShadow shadow = new DropShadow(7, Color.DARKGRAY);
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        setEffect(shadow);
        getStyleClass().add("card");
    }

    /**
     * Construct a Card with the provided title displayed.
     *
     * @param title the title to display.
     */
    public Card(String title) {
        this();

        //title
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("title");
        header = new HBox(titleLabel);
        header.setAlignment(Pos.CENTER);

        cardContent.getChildren().add(0, header);
    }

    /**
     * Set the content to be displayed on the card.
     *
     * @param node Node of the content to display.
     */
    public void setDisplayedContent(Node node) {
        displayedContent.getChildren().setAll(node);
    }

    /**
     * Set content to display at the bottom of the card such as buttons.
     *
     * @param node Node of the content to display.
     */
    public void setFooterContent(Node node) {
        //lazy load
        if (footerContent == null) {
            footerContent = new StackPane();
            footerContent.setMaxHeight(35);
            footerContent.getStyleClass().add("footer");
            cardContent.getChildren().add(footerContent);
        }
        footerContent.getChildren().setAll(node);
    }

    /**
     * Return the header container for modification. Contains the title label.
     * @return the header container for modification. Contains the title label.
     */
    protected HBox getHeader() {
        return header;
    }

}
