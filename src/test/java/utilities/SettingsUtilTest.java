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

import objects.RecommendedFile;
import objects.Theme;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import settings.Settings;
import validation.ValidationType;

import java.io.File;

import static org.junit.Assert.*;

public class SettingsUtilTest extends ApplicationTest {

    @Test
    public void getSettings() {
        assertNotNull(SettingsUtil.getSettings());
    }

    @Test
    public void addRecommendedFile() {
        String path = "/test/path";
        File file = new File(path);
        SettingsUtil.addRecommendedFile(file);
        Settings settings = SettingsUtil.getSettings();
        RecommendedFile result = settings.getRecommendedFiles().stream()
                .filter(rf -> path.equalsIgnoreCase(rf.getPath()))
                .findFirst().get();

        assertNotNull(result);
        assertEquals(path, result.getPath());
        SettingsUtil.removeRecommendedFile(result);
    }

    @Test
    public void saveSettings() {
        Settings settings = new Settings();
        settings.setValidationType(ValidationType.STRICT);
        settings.setTheme(Theme.DARK);
        settings.setProjectDirectory(null);
        SettingsUtil.saveSettings(settings);
    }
}