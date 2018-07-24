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
