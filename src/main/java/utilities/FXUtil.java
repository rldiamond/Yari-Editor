/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class FXUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXUtil.class);

    /**
     * Run the provided runnable on the FX thread.
     *
     * @param run the task to run on the FX thread.
     */
    public static void runOnFXThread(Runnable run) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(run);
        } else {
            run.run();
        }
    }

    /**
     * Run the supplied task on a non-fx thread thread.
     * @param run
     */
    public static void runAsync(Runnable run) {
        new Thread(run::run).start();
    }

    /**
     * Initialize FXML based on naming conventions.
     */
    public static void intializeFXML(Node view) {
        String fxmlName = view.getClass().getSimpleName() + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(view.getClass().getResource(fxmlName));
        fxmlLoader.setController(view);
        fxmlLoader.setRoot(view);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.error("Failed to load FXML for view '{}'!", view.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    // --------------- ANIMATIONS --------------------

    public static FadeTransition installFade(Node node, AnimationFadeType animationFadeType) {
        var from = 0;
        var to = 0;

        FadeTransition fade = new FadeTransition();

        switch (animationFadeType) {
            case IN:
                from = 0;
                to = 1;
                break;
            case OUT:
                from = 1;
                to = 0;
                fade.setOnFinished(e -> {
                    node.setManaged(false);
                    node.setVisible(false);
                });
                break;
            default:
                //..
        }

        fade.setDuration(Duration.millis(200));
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.setNode(node);
        return fade;
    }

    public enum AnimationFadeType {
        IN, OUT
    }

    public enum AnimationDirection {
        RIGHT, LEFT, UP, DOWN
    }

    public static Timeline installBump(Node node, AnimationDirection animationDirection) {
        final var distance = 5;
        final var startX = node.getTranslateX();
        final var startY = node.getTranslateY();
        var endX = node.getTranslateX();
        var endY = node.getTranslateY();

        switch (animationDirection) {
            case RIGHT:
                endX += distance;
                break;
            case LEFT:
                endX -= distance;
                break;
            case UP:
                endY += distance;
                break;
            case DOWN:
                endY -= distance;
                break;
            default:
                //..
        }

        Timeline bump = new Timeline();
        bump.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), startX),
                        new KeyValue(node.translateYProperty(), startY)
                ),
                new KeyFrame(new Duration(150),
                        new KeyValue(node.translateXProperty(), endX),
                        new KeyValue(node.translateYProperty(), endY)
                )
        );
        return bump;
    }

    public static Timeline installBumpBack(Node node, AnimationDirection originatingDirection) {
        final var distance = 5;
        var startX = node.getTranslateX();
        var startY = node.getTranslateY();
        var endX = node.getTranslateX();
        var endY = node.getTranslateY();

        switch (originatingDirection) {
            case RIGHT:
                startX += distance;
                break;
            case LEFT:
                startX -= distance;
                break;
            case DOWN:
                startY += distance;
                break;
            case UP:
                startY -= distance;
                break;
            default:
                //..
        }

        Timeline bump = new Timeline();
        bump.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), startX),
                        new KeyValue(node.translateYProperty(), startY)
                ),
                new KeyFrame(new Duration(150),
                        new KeyValue(node.translateXProperty(), endX),
                        new KeyValue(node.translateYProperty(), endY)
                )
        );
        return bump;

    }
}
