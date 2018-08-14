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

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHelper {

    /**
     * Private constructor to hide the implicit public constructor.
     */
    private ResizeHelper() {

    }

    /**
     * Add an EventHandler to the provided node which will 'maximize' the provided Stage on double-click if not already,
     * or return the stage to it's previous size otherwise.
     *
     * @param stage the Stage to resize.
     * @param node  the Node to apply listeners to.
     */
    public static void addHeaderResizeListener(Stage stage, Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new HeaderResizeHandler(stage, 2));
    }

    /**
     * Add an EventHandler to the provided node which will 'maximize' the provided Stage on single-click if not already,
     * or return the stage to it's previous size otherwise.
     *
     * @param stage the Stage to resize.
     * @param node  the Node to apply listeners to.
     */
    public static void addMaximizeResizeListener(Stage stage, Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new HeaderResizeHandler(stage, 1));
    }

    /**
     * Add an EventHandler to the provided node which will allow for undecorated stages to be moved via mouse drag.
     *
     * @param stage the Stage to move.
     * @param node  the Node to apply listeners to.
     */
    public static void addUndecoratedStageDragListener(Stage stage, Node node) {
        UndecoratedWindowDragHandler undecoratedWindowDragHandler = new UndecoratedWindowDragHandler(stage);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, undecoratedWindowDragHandler);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, undecoratedWindowDragHandler);
    }

    /**
     * Adds a resize listener to an undecorated stage, allowing it to be resized by clicking and dragging any edge of the
     * stage.
     *
     * @param stage the Stage to resize.
     */
    public static void addStageResizeListener(Stage stage) {
        addStageResizeListener(stage, 1000, 550, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Minimizes the provided Stage.
     *
     * @param stage the Stage to minimize.
     */
    public static void minimizeStage(Stage stage) {
        stage.setIconified(true);
    }


    private static void addStageResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        resizeListener.setMinWidth(minWidth);
        resizeListener.setMinHeight(minHeight);
        resizeListener.setMaxWidth(maxWidth);
        resizeListener.setMaxHeight(maxHeight);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }
}