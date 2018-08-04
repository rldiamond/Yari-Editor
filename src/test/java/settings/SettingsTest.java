/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package settings;

import objects.Theme;
import org.junit.Before;
import org.junit.Test;
import validation.ValidationType;

import java.io.File;

import static org.junit.Assert.*;

public class SettingsTest {

    Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = new Settings();
        settings.setTheme(Theme.DARK);
        settings.setProjectDirectory("/test/dir");
        settings.setValidationType(ValidationType.DISABLED);
    }

    @Test
    public void getProjectDirectory() {
        assertEquals("/test/dir", settings.getProjectDirectory());
    }

    @Test
    public void setProjectDirectory() {
        assertEquals("/test/dir", settings.getProjectDirectory());
        String updatedDir = "/updated/dir";
        settings.setProjectDirectory(updatedDir);
        assertEquals(updatedDir, settings.getProjectDirectory());
    }

    @Test
    public void getValidationType() {
        assertEquals(ValidationType.DISABLED, settings.getValidationType());
    }

    @Test
    public void setValidationType() {
        assertEquals(ValidationType.DISABLED, settings.getValidationType());
        ValidationType updatedType = ValidationType.NORMAL;
        settings.setValidationType(updatedType);
        assertEquals(updatedType, settings.getValidationType());
    }

    @Test
    public void getTheme() {
        assertEquals(Theme.DARK, settings.getTheme());
    }

    @Test
    public void setTheme() {
        assertEquals(Theme.DARK, settings.getTheme());
        settings.setTheme(null);
        assertNull(settings.getTheme());
    }

    @Test
    public void addRecentFile() {
        assertNull(settings.getRecommendedFiles());
        File file = new File("test");
        settings.addRecentFile(file);
        assertEquals(1, settings.getRecommendedFiles().size());

        //we expect there to be no more than 5
        for (int i = 0; i < 10; i++) {
            File file1 = new File("test" + i);
            settings.addRecentFile(file1);
        }

        assertEquals(5, settings.getRecommendedFiles().size());
    }

    @Test
    public void getRecommendedFiles() {
        assertNull(settings.getRecommendedFiles());
        File file = new File("test");
        settings.addRecentFile(file);
        assertEquals(1, settings.getRecommendedFiles().size());
    }
}