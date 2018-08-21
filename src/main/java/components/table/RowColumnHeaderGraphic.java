/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components.table;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import objects.ComparatorType;
import objects.DataType;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import utilities.FXUtil;

/**
 * Custom Row column header graphic to help the user more quickly understand the column's required data.
 * The sticky property can be set with {@link RowColumnHeaderGraphic#setSticky(boolean)}, causing the
 * column headers to continuously display the 'subtext' or fade it in and out on mouse hover.
 */
public class RowColumnHeaderGraphic extends StackPane {

    private static BooleanProperty sticky = new SimpleBooleanProperty(false);

    private DataType dataType;
    private ComparatorType comparatorType;
    private String title;

    /**
     * Build a RowColumnHeaderGraphic with a TableCondition.
     *
     * @param tableCondition the TableCondition to build the graphic with.
     */
    RowColumnHeaderGraphic(TableCondition tableCondition) {
        if (tableCondition == null) {
            return;
        }
        dataType = DataType.getFromTableString(tableCondition.getDataType());
        comparatorType = ComparatorType.getFromTableString(tableCondition.getComparator());
        title = tableCondition.getName();
        initialize();
    }

    /**
     * Build a RowColumnHeaderGraphic with a TableAction.
     *
     * @param tableAction the TableAction to build the graphic with.
     */
    RowColumnHeaderGraphic(TableAction tableAction) {
        if (tableAction == null) {
            return;
        }
        dataType = DataType.getFromTableString(tableAction.getDataType());
        title = tableAction.getName();
        initialize();
    }

    private void initialize() {
        super.getStyleClass().add("rowHeader");
        VBox container = new VBox(3);
        container.setAlignment(Pos.CENTER);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("titleText");
        Label subtext = new Label();
        String subTextContent = dataType.getDisplayValue();
        if (comparatorType != null){
            subTextContent += ", " + comparatorType.getDisplayValue();
        }
        subtext.setText(subTextContent);
        subtext.getStyleClass().add("subText");

        HeaderMouseEventHandler mouseEventHandler = new HeaderMouseEventHandler(subtext);

        if (!sticky.get()){
            subtext.setVisible(false);
            subtext.setManaged(false);
            super.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEventHandler);
            super.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEventHandler);
        }

        sticky.addListener((obs, ov, isSticky) -> {

            if (isSticky) {
                    super.removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEventHandler);
                    super.removeEventHandler(MouseEvent.MOUSE_EXITED, mouseEventHandler);
                    subtext.setVisible(true);
                    subtext.setOpacity(1);
                    subtext.setManaged(true);
            } else {
                    subtext.setVisible(false);
                    subtext.setManaged(false);
                    super.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEventHandler);
                    super.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEventHandler);
            }

        });


        container.getChildren().addAll(subtext, titleLabel);

        //tooltip
        StringBuilder ttBuilder = new StringBuilder();
        ttBuilder.append(title).append(" ");
        if (dataType != null) {
            ttBuilder.append("(").append(dataType.getDisplayValue());
        }

        if (comparatorType != null) {
            ttBuilder.append(", ").append(comparatorType.getDisplayValue());
        }
        ttBuilder.append(")");

        Tooltip.install(container, new Tooltip(ttBuilder.toString()));


        super.getChildren().setAll(container);
    }

    /**
     * Set the sticky property for the graphic. If sticky, the graphic will display the 'subtext' continuously.
     * If not sticky, the graphic will fade the 'subtext' in and out on mouse hover.
     *
     * @param isSticky true to toggle sticky headers.
     */
    public static void setSticky(boolean isSticky) {
        sticky.set(isSticky);
    }

    /**
     * Inner MouseEvent EventHandler class to handle the fading of the text in and out on mouse enter or exit.
     */
    private class HeaderMouseEventHandler implements EventHandler<MouseEvent> {
        FadeTransition fadeIn;
        FadeTransition fadeOut;
        Label headerLabel;

        /**
         * Build the MouseEventHandler with the provided Label.
         *
         * @param label the Label to fade in and out.
         */
        HeaderMouseEventHandler(Label label) {
            this.headerLabel = label;
            fadeIn = FXUtil.installFade(label, FXUtil.AnimationFadeType.IN, Duration.millis(100));
            fadeOut = FXUtil.installFade(label, FXUtil.AnimationFadeType.OUT, Duration.millis(100));
        }

        @Override
        public void handle(MouseEvent event) {

            final EventType<? extends MouseEvent> eventType = event.getEventType();

            if (MouseEvent.MOUSE_ENTERED.equals(eventType)) {
                headerLabel.setManaged(true);
                headerLabel.setOpacity(0);
                headerLabel.setVisible(true);
                fadeOut.stop();
                fadeIn.play();
            } else if (MouseEvent.MOUSE_EXITED.equals(eventType)) {
                fadeIn.stop();
                fadeOut.play();
            }
        }
    }
}
