package utilities;

import com.jfoenix.controls.JFXSnackbar;
import view.RootLayoutFactory;

public class ToastUtil {

    public static void sendPersistantToast(String message){
        RootLayoutFactory.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(message,
                "DISMISS",
                3000,
                true,
                b -> RootLayoutFactory.getInstance().getToastBar().close()));
    }

    public static void sendToast(String message){
        RootLayoutFactory.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(message));
    }
}
