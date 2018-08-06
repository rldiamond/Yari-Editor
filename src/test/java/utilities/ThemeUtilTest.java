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

import javafx.scene.Scene;
import javafx.scene.control.Label;
import objects.Theme;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class ThemeUtilTest extends ApplicationTest {

    @Test
    public void setActiveTheme() {
        Theme expected = Theme.DARK;
        ThemeUtil.setActiveTheme(expected);
        assertEquals(expected, ThemeUtil.getActiveTheme());
    }

    @Test
    public void getActiveTheme() {
        Theme expected = Theme.DARK;
        ThemeUtil.setActiveTheme(expected);
        assertEquals(expected, ThemeUtil.getActiveTheme());
    }

    @Test
    public void setThemeOnScene() {
        Scene scene = new Scene(new Label());

        assertTrue(scene.getStylesheets().isEmpty());
        FXUtil.runOnFXThread(() -> {
            ThemeUtil.setThemeOnScene(scene);
            assertEquals(1, scene.getStylesheets().size());
        });
    }

    @Test
    public void getLogo() {
        assertNotNull(ThemeUtil.getLogo());
    }

    @Test
    public void refreshThemes() {
        ThemeUtil.refreshThemes();
    }
}