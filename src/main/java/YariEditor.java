/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.yari.core.table.DecisionTable;
import utilities.DecisionTableValidator;
import utilities.FXUtil;
import utilities.FileUtil;
import utilities.ThemeUtil;
import view.RootLayoutFactory;
import view.WelcomeSplash;
import view.WelcomeSplashFactory;

public class YariEditor extends Application {

    private static Stage primaryStage;

    //for dragging undecorated windows
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        YariEditor.primaryStage = primaryStage;
        //show the welcome splash screen
        WelcomeSplash welcomeSplash = WelcomeSplashFactory.getInstance();
        Scene splashScene = new Scene(welcomeSplash);
        ThemeUtil.setThemeOnScene(splashScene);

        //allow the welcome splash to be moved
        welcomeSplash.getDragBar().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        welcomeSplash.getDragBar().setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(splashScene);

        //options
        welcomeSplash.getCreateNewButton().setOnMouseClicked(me -> {
            FXUtil.runAsync(() -> {
                RootLayoutFactory.getInstance().setDecisionTable(new DecisionTable());
                FXUtil.runOnFXThread(() -> RootLayoutFactory.show(primaryStage));
            });
        });

        welcomeSplash.getOpenButton().setOnMouseClicked(me -> {
            welcomeSplash.busyProperty().set(true);
            FileUtil.openFile(primaryStage);
        });

//        welcomeSplash.getOpenButton().setOnMouseClicked(me -> {
//            welcomeSplash.busyProperty().set(true);
//            boolean success = false;
//            DecisionTableValidator.setEnabled(false);
//
//            try {
//                success = FileUtil.openFile(primaryStage);
//            } catch (Exception ex) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Could not load table data");
//                alert.setContentText("Could not load table data from file.\n" + ex.getMessage());
//                alert.showAndWait();
//                welcomeSplash.busyProperty().set(false);
//            }
//            if (success) {
//                FXUtil.runAsync(() -> {
//                    //temp disable validation
//                    RootLayoutFactory.init();
//
//                    FXUtil.runOnFXThread(() -> {
//                        RootLayoutFactory.show(primaryStage);
//                        welcomeSplash.busyProperty().set(false);
//                    });
//                });
//            } else {
//                welcomeSplash.busyProperty().set(false);
//            }
//            DecisionTableValidator.setEnabled(true);
//
//        });


        primaryStage.show();

    }

}
