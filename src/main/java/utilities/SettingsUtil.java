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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import objects.RecommendedFile;
import objects.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import settings.Settings;
import validation.ValidationService;
import validation.ValidationType;

import java.io.File;


/**
 * Loads, saves, and maintains the active user {@link Settings}.
 */
public class SettingsUtil {

    private static ObjectProperty<Settings> settingsProperty = new SimpleObjectProperty<>(null);

    private SettingsUtil() {
        settingsProperty.addListener(c -> updateApplicationServices());
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
     * Add a recommended file to the settings.
     *
     * @param file the file to add.
     */
    static void addRecommendedFile(File file) {
        Settings settings = getSettings();
        settings.addRecentFile(file);
        saveSettings(settings);
    }

    /**
     * Remove a recommended file from the settings.
     *
     * @param recommendedFile the file to remove.
     */
    public static void removeRecommendedFile(RecommendedFile recommendedFile) {
        if (recommendedFile != null && getSettings().getRecommendedFiles().contains(recommendedFile)) {
            getSettings().getRecommendedFiles().remove(recommendedFile);
            saveSettings(getSettings());
        }
    }

    /**
     * Load settings from the User directory.
     */
    private static void loadSettings() {
        Settings settings = FileUtil.loadObjectFromFile(Settings.class, getUserDirectory());

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
        FileUtil.saveObjectToFile(settings, getUserDirectory());
        setSettings(settings);
    }

    private static void setSettings(Settings settings) {
        settingsProperty.set(settings);
        updateApplicationServices();
    }

    private static void updateApplicationServices() {
        Settings newestSettings = getSettings();
        ValidationService.getService().setEnabled(!newestSettings.getValidationType().equals(ValidationType.DISABLED));
        ValidationService.getService().setStrict(!newestSettings.getValidationType().equals(ValidationType.STRICT));
        ThemeUtil.setActiveTheme(newestSettings.getTheme());
        ThemeUtil.refreshThemes();
    }

    private static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".yariEditorSettings";
    }

    /**
     * Load base default settings.
     */
    private static Settings getDefaults() {
        Settings defaultSettings = new Settings();
        defaultSettings.setTheme(Theme.DARK);
        defaultSettings.setValidationType(ValidationType.STRICT);
        defaultSettings.setProjectDirectory(null);
        return defaultSettings;
    }

}
