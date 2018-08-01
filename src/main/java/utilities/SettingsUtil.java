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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import objects.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import settings.Settings;
import validation.ValidationService;
import validation.ValidationType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * Loads, saves, and maintains the active user {@link Settings}.
 */
public class SettingsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SettingsUtil.class);
    private static ObjectProperty<Settings> settingsProperty = new SimpleObjectProperty<>(null);

    private SettingsUtil() {
        settingsProperty.addListener((obs, ov, nv) -> {
            updateApplicationServices();
        });
    }

    /**
     * Retrieve the currently active set of user settings.
     *
     * @return the currently active set of user settings.
     */
    public static Settings getSettings() {
        if (settingsProperty.get() == null) {
            loadSettings();
        }
        return settingsProperty.get();
    }

    /**
     * Load settings from the User directory.
     */
    private static void loadSettings() {
        XStream xStream = new XStream();
        Settings settings = null;
        try (FileReader reader = new FileReader(getUserDirectory())) {
            settings = (Settings) xStream.fromXML(reader);
        } catch (IOException ex) {
            logger.error("Failed to load settings from the user directory.");
        }

        if (settings == null) {
            //settings not found, let's save the default settings
            saveSettings(getDefaults());
        } else {
            setSettings(settings);
        }
    }

    /**
     * Save the supplied settings to the user directory and apply changes to the application services.
     *
     * @param settings settings to save.
     */
    public static void saveSettings(Settings settings) {
        FXUtil.runAsync(() -> {

            String dir = getUserDirectory();
            File file = new File(dir);

            XStream xStream = new XStream();
            try (FileOutputStream out = new FileOutputStream(file)) {
                xStream.toXML(settings, out);
                setSettings(settings);
            } catch (Exception ex) {
                logger.error("Failed to save settings to the user directory.", ex);
            }

        });
    }

    private static void setSettings(Settings settings){
        settingsProperty.set(settings);
        updateApplicationServices();
    }

    private static void updateApplicationServices() {
        Settings newestSettings = getSettings();
        ValidationService.getService().setEnabled(!newestSettings.getValidationType().equals(ValidationType.DISABLED));
        ValidationService.getService().setStrict(!newestSettings.getValidationType().equals(ValidationType.STRICT));
        ThemeUtil.setActiveTheme(newestSettings.getTheme());
    }

    private static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".yariEditorSettings" + File.separator;
    }

    /**
     * Load base default settings.
     */
    private static Settings getDefaults() {
        Settings defaultSettings = new Settings();
        defaultSettings.setTheme(Theme.DARK);
        defaultSettings.setValidationType(ValidationType.STRICT);
        defaultSettings.setProjectDirectory(null);
        defaultSettings.setAutoSave(false);
        return defaultSettings;
    }

}
