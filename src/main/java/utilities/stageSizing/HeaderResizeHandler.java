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

package utilities.stageSizing;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * An EventHandler listening for double-clicks on the node. If double clicked and primary button, the stage will be
 * resized. The stage will 'maximize' if not already, or return to a previous state otherwise.
 */
public class HeaderResizeHandler implements EventHandler<MouseEvent> {

    private final Stage stage;

    private double beforeX;
    private double beforeY;
    private double beforeWidth;
    private double beforeHeight;

    /**
     * Create a new HeaderResizeHandler with the provided Stage object.
     *
     * @param stage the Stage object to resize.
     */
    protected HeaderResizeHandler(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY || event.getClickCount() != 2) {
            return;
        }

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        //basically if maximized
        if (stage.getWidth() == visualBounds.getWidth() && stage.getHeight() == visualBounds.getHeight()) {
            stage.setX(beforeX);
            stage.setY(beforeY);
            stage.setWidth(beforeWidth);
            stage.setHeight(beforeHeight);
        } else {
            beforeHeight = stage.getHeight();
            beforeWidth = stage.getWidth();
            beforeX = stage.getX();
            beforeY = stage.getY();

            stage.setX(visualBounds.getMinX());
            stage.setY(visualBounds.getMinY());
            stage.setWidth(visualBounds.getWidth());
            stage.setHeight(visualBounds.getHeight());
        }

    }
}
