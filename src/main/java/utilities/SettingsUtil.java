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

import objects.Settings;
import objects.Theme;

/**
 * Loads, saves, and maintains the active user {@link Settings}.
 */
public class SettingsUtil {

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

    /**
     * Load base default settings.
     */
    private static void loadDefaults() {
        activeSettings = new Settings();
        activeSettings.setTheme(Theme.DARK);

        activeSettings.setValidationEnabled(true);
        activeSettings.setStrictValidation(true);
    }

}
