/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlertDialogTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        AlertDialog alertDialog = new AlertDialog(primaryStage, AlertDialog.DialogType.ERROR);
        alertDialog.setTitle("Error");
        alertDialog.setBody("An error has occurred. Please check what you did wrong and fix it. Thank you and have a wonderful day.");
        primaryStage.setScene(new Scene(alertDialog));
        primaryStage.show();
    }
}
