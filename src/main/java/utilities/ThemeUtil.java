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

import javafx.scene.Scene;
import javafx.scene.image.Image;
import objects.Theme;
import view.RootLayoutFactory;

/**
 * Provide application-wide support for themes. Currently implemented: Dark Theme.
 */
public class ThemeUtil {

    private static Theme activeTheme;
    private static final Image LOGO = new Image("/theme/YariLogo.png");

    /**
     * Private constructor to hide the implicit public constructor.
     */
    private ThemeUtil()
    {

    }

    /**
     * Sets the currently active theme.
     *
     * @param theme the theme to set.
     */
    public static void setActiveTheme(Theme theme) {
        activeTheme = theme;
        SettingsUtil.getSettings().setTheme(theme);
    }

    /**
     * Returns the currently active theme.
     *
     * @return the active theme.
     */
    public static Theme getActiveTheme() {
        if (activeTheme == null) {
            //load theme from settings
            activeTheme = SettingsUtil.getSettings().getTheme();
        }
        return activeTheme;
    }

    /**
     * Convenience method to set the currently active theme on a scene.
     *
     * @param scene themed scene.
     */
    public static void setThemeOnScene(Scene scene) {
        if (scene != null) {
            FXUtil.runOnFXThread(() -> {
                scene.getStylesheets().setAll(getActiveTheme().getCss());
            });
        }
    }

    public static Image getLogo() {
        return LOGO;
    }

    /**
     * Refresh the themes on active scenes. Call if changing theme.
     */
    public static void refreshThemes() {
        setThemeOnScene(RootLayoutFactory.getScene());
    }

}
