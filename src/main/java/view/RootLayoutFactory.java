/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package view;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utilities.FileUtil;
import utilities.KeyboardShortcut;
import utilities.ThemeUtil;

import java.util.Arrays;

public class RootLayoutFactory {

    private static RootLayout rootLayout;
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static boolean displayed = false;

    public static RootLayout getInstance() {
        if (rootLayout == null) {
            rootLayout = new RootLayout();
        }
        return rootLayout;
    }

    public static void show(Stage stage) {
        Scene scene = new Scene(getInstance());
        ThemeUtil.setThemeOnScene(scene);

        //take note of position of frame, so when we show the new frame, we can center it on the previous location.
        double oldXCenterPosition = stage.getX() + stage.getWidth() / 2d;
        double oldYCenterPosition = stage.getY() + stage.getHeight() / 2d;

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(550);

        //add keyboard shortcuts
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            Arrays.stream(KeyboardShortcut.values())
                    .filter(value -> value.getKeyCombination().match(event))
                    .findFirst()
                    .ifPresent(KeyboardShortcut::run);
        });

        //make moveable
        getInstance().getHeader().setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        getInstance().getHeader().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        //allow minimize
        getInstance().getMinimizeButton().setOnMouseClicked(me -> {
            stage.setIconified(true);
        });

        stage.setX(oldXCenterPosition - stage.getWidth() / 2d);
        stage.setY(oldYCenterPosition - stage.getHeight() / 2d);
        displayed = true;
        stage.show();

    }

    public static boolean isDisplayed() {
        return displayed;
    }
}
