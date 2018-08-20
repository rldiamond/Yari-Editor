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
import objects.ComparatorType;
import objects.DataType;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import utilities.FXUtil;

public class HoverableRowColumnHeaderGraphic extends StackPane {

    private static BooleanProperty sticky = new SimpleBooleanProperty(false);

    private DataType dataType;
    private ComparatorType comparatorType;
    private String title;

    public HoverableRowColumnHeaderGraphic(TableCondition tableCondition) {
        if (tableCondition == null) {
            return;
        }
        dataType = DataType.getFromTableString(tableCondition.getDataType());
        comparatorType = ComparatorType.getFromTableString(tableCondition.getComparator());
        title = tableCondition.getName();
        initialize();
    }

    public HoverableRowColumnHeaderGraphic(TableAction tableAction) {
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

        if (!sticky.get()){
            subtext.setVisible(false);
            subtext.setManaged(false);

            super.setOnMouseEntered(me -> {
                subtext.setVisible(true);
                subtext.setManaged(true);
            });

            super.setOnMouseExited(me -> {
                subtext.setVisible(false);
                subtext.setManaged(false);
            });
        }

        sticky.addListener((obs, ov, isSticky) -> {
           if (isSticky){

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

    private class HeaderMouseEventHandler implements EventHandler<MouseEvent> {
        FadeTransition fadeIn;
        FadeTransition fadeOut;

        public HeaderMouseEventHandler(Label headerLabel) {
            fadeIn = FXUtil.installFade(headerLabel, FXUtil.AnimationFadeType.IN);
            fadeOut = FXUtil.installFade(headerLabel, FXUtil.AnimationFadeType.OUT);
        }

        @Override
        public void handle(MouseEvent event) {

            final EventType<? extends MouseEvent> eventType = event.getEventType();

            if (MouseEvent.MOUSE_ENTERED.equals(eventType)) {
                fadeOut.stop();
                fadeIn.play();
            } else if (MouseEvent.MOUSE_EXITED.equals(eventType)) {
                fadeIn.stop();
                fadeOut.play();
            }
        }
    }
}
