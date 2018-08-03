/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHelper {

    /**
     * Private constructor to hide the implicit public constructor.
     */
    private ResizeHelper() {
    }

    public static void addResizeListener(Stage stage) {
        addResizeListener(stage, 1000, 550, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
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

    static class ResizeListener implements EventHandler<MouseEvent> {
        private Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private int border = 4;
        private double startX = 0;
        private double startY = 0;

        // Max and min sizes for controlled stage
        private double minWidth;
        private double maxWidth;
        private double minHeight;
        private double maxHeight;

        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        public void setMinWidth(double minWidth) {
            this.minWidth = minWidth;
        }

        public void setMaxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMinHeight(double minHeight) {
            this.minHeight = minHeight;
        }

        public void setMaxHeight(double maxHeight) {
            this.maxHeight = maxHeight;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX();
            double mouseEventY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                scene.setCursor(cursorEvent);
            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                startX = stage.getWidth() - mouseEventX;
                startY = stage.getHeight() - mouseEventY;
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && !Cursor.DEFAULT.equals(cursorEvent) && !Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                double minimumHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
                if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent)
                        || Cursor.NE_RESIZE.equals(cursorEvent)) {
                    if (stage.getHeight() > minimumHeight || mouseEventY < 0) {
                        setStageHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                        stage.setY(mouseEvent.getScreenY());
                    }
                } else {
                    if (stage.getHeight() > minimumHeight || mouseEventY + startY - stage.getHeight() > 0) {
                        setStageHeight(mouseEventY + startY);
                    }
                }
                if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                    double minimumWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
                    if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent)
                            || Cursor.SW_RESIZE.equals(cursorEvent)) {
                        if (stage.getWidth() > minimumWidth || mouseEventX < 0) {
                            setStageWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                            stage.setX(mouseEvent.getScreenX());
                        }
                    } else {
                        if (stage.getWidth() > minimumWidth || mouseEventX + startX - stage.getWidth() > 0) {
                            setStageWidth(mouseEventX + startX);
                        }
                    }
                }
            }
        }

        private void setStageWidth(double width) {
            width = Math.min(width, maxWidth);
            width = Math.max(width, minWidth);
            stage.setWidth(width);
        }

        private void setStageHeight(double height) {
            height = Math.min(height, maxHeight);
            height = Math.max(height, minHeight);
            stage.setHeight(height);
        }

    }
}