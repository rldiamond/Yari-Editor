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

import com.thoughtworks.xstream.XStream;
import objects.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import settings.Settings;
import validation.ValidationType;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Loads, saves, and maintains the active user {@link Settings}.
 */
public class SettingsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SettingsUtil.class);
    private static Settings activeSettings;

    /**
     * Retrieve the currently active set of user settings.
     *
     * @return the currently active set of user settings.
     */
    public static Settings getSettings() {
        if (activeSettings == null) {
            loadDefaults();
        }
        return activeSettings;
    }

    public static void loadSettings() {
        //TODO: load via xstream
        updateApplicationServices();
    }

    public static void saveSettings(Settings settings) {
        String dir = getUserDirectory();
        File file = new File(dir);

        XStream xStream = new XStream();
        try (FileOutputStream out = new FileOutputStream(file)) {
            xStream.toXML(settings, out);
            activeSettings = settings;
            updateApplicationServices();
        } catch (Exception ex) {
            logger.error("Failed to save settings to the user directory.", ex);
            //TODO: Do a thing
        }

    }

    private static void updateApplicationServices() {
        //TODO: When settings change, application services will behave differently. here, we change the settings in them.
    }

    private static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".yariEditorSettings" + File.separator;
    }

    /**
     * Load base default settings.
     */
    private static void loadDefaults() {
        activeSettings = new Settings();
        activeSettings.setTheme(Theme.DARK);
        activeSettings.setValidationType(ValidationType.STRICT);
        activeSettings.setProjectDirectory(null);
        activeSettings.setAutoSave(false);
    }

}
