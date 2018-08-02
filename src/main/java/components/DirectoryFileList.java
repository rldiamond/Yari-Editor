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

import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.FileUtil;
import utilities.SettingsUtil;

import java.io.File;

public class DirectoryFileList extends ListView<File> {

    public DirectoryFileList(Stage stage) {

        getStyleClass().add("fileList");

        File f = new File(SettingsUtil.getSettings().getProjectDirectory());
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith("xml"));

        setItems(FXCollections.observableArrayList(matchingFiles));
        setCellFactory(c -> new FileListCell(stage));

        //if there's nothing to display, don't display
        if (matchingFiles == null || matchingFiles.length == 0) {
            setManaged(false);
            setVisible(false);
        }
    }

    private class FileListCell extends ListCell<File> {

        private final Stage stage;

        public FileListCell(Stage stage) {
            this.stage = stage;

            setOnMouseClicked(me -> {
                if (getItem() != null) {
                    FileUtil.openFile(getItem(), stage);
                }
            });
        }

        @Override
        protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                setText(item.getName());
                Pane image = new Pane();
                image.getStyleClass().add("fileIcon");
                image.setPrefSize(12, 12);
                setGraphic(image);
            }
        }
    }

}
