/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities.stageSizing;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UndecoratedWindowDragHandler implements EventHandler<MouseEvent> {

    private final Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    protected UndecoratedWindowDragHandler(Stage stage){
        this.stage = stage;

    }

    @Override
    public void handle(MouseEvent event) {

        final EventType<? extends MouseEvent> eventType = event.getEventType();

        if (MouseEvent.MOUSE_PRESSED.equals(eventType)){
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } else if (MouseEvent.MOUSE_DRAGGED.equals(eventType)){
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }

    }
}
