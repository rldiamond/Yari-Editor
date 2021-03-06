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
package utilities;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

public class FXUtil {

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
     *
     * @param run the task to run asynchronously.
     */
    public static void runAsync(Runnable run) {
        new Thread(run::run).start();
    }

    // --------------- ANIMATIONS --------------------

    /**
     * Install a FadeTransition on the provided Node. The {@link AnimationFadeType} determines whether the
     * transition should be a fade in, or fade out. This method provides a default duration of 200ms.
     * <p>
     * At the end of the fade, we set the Node's managed property to FALSE and the Node's visible property
     * to FALSE.
     *
     * @param node              the Node to install the fade onto.
     * @param animationFadeType whether the fade should fade in or fade out.
     * @return a complete FadeTransition ready to {@link FadeTransition#play()}.
     */
    public static FadeTransition installFade(Node node, AnimationFadeType animationFadeType) {
        return installFade(node, animationFadeType, Duration.millis(200));
    }

    /**
     * Install a FadeTransition on the provided Node. The {@link AnimationFadeType} determines whether the
     * transition should be a fade in, or fade out. This method provides the ability to set a custom Duration.
     * <p>
     * At the end of the fade, we set the Node's managed property to FALSE and the Node's visible property
     * to FALSE.
     *
     * @param node              the Node to install the fade onto.
     * @param animationFadeType whether the fade should fade in or fade out.
     * @param duration          the Duration to run the fade for.
     * @return a complete FadeTransition ready to {@link FadeTransition#play()}.
     */
    public static FadeTransition installFade(Node node, AnimationFadeType animationFadeType, Duration duration) {
        int from = 0;
        int to = 0;

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

        fade.setDuration(duration);
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
        final int distance = 5;
        final double startX = node.getTranslateX();
        final double startY = node.getTranslateY();
        double endX = node.getTranslateX();
        double endY = node.getTranslateY();

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
        final int distance = 5;
        double startX = node.getTranslateX();
        double startY = node.getTranslateY();
        double endX = node.getTranslateX();
        double endY = node.getTranslateY();

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
