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

import com.jfoenix.controls.JFXSnackbar;
import view.RootLayoutFactory;

/**
 * Convenience methods to easily send a toast notification.
 */
public class ToastUtil {

    /**
     * Sends a persistent toast, which the user must dismiss with a dismiss button
     *
     * @param message the message to display in the toast.
     */
    public static void sendPersistentToast(String message) {
        RootLayoutFactory.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(message,
                "DISMISS",
                3000,
                false,
                b -> RootLayoutFactory.getInstance().getToastBar().close()));
    }

    /**
     * Send a standard toast which automatically dismisses itself after a short period of time.
     *
     * @param message the message to display in the toast.
     */
    public static void sendToast(String message) {
        RootLayoutFactory.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(message));
    }
}
