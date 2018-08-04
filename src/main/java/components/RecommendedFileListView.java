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

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import objects.RecommendedFile;
import settings.Settings;
import utilities.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom ListView for displaying recommended files.
 */
public class RecommendedFileListView extends ListView<RecommendedFile> {

    private final BooleanProperty busy;

    /**
     * Build the list view with the supplied stage and busy property.
     *
     * @param stage the stage to utilize in actions.
     * @param busy  will notify when busy.
     */
    public RecommendedFileListView(Stage stage, BooleanProperty busy, Settings settings) {
        this.busy = busy;

        getStyleClass().add("fileList");

        File[] matchingFiles = null;
        String path = settings.getProjectDirectory();
        if (path != null) {
            File f = new File(path);
            matchingFiles = f.listFiles((dir, name) -> name.endsWith("xml"));
        }

        Set<RecommendedFile> projectDirFiles;

        if (matchingFiles == null) {
            projectDirFiles = new HashSet<>();
        } else {
            projectDirFiles = Arrays.stream(matchingFiles)
                    .map(RecommendedFile::new)
                    .collect(Collectors.toSet());
        }

        List<RecommendedFile> filesFromSettings = settings.getRecommendedFiles();
        if (filesFromSettings != null) {
            projectDirFiles.addAll(filesFromSettings);

        }

        setItems(FXCollections.observableArrayList(projectDirFiles));
        setCellFactory(c -> new FileListCell(stage));
        getItems().addListener((ListChangeListener.Change<? extends RecommendedFile> c) -> {
            c.next();
            refresh();
        });

    }

    /**
     * Custom ListCell for RecommendedFiles to provide opening logic and custom graphics.
     */
    public class FileListCell extends ListCell<RecommendedFile> {

        FileListCell(Stage stage) {
            setOnMouseClicked(me -> {
                if (me.getClickCount() == 2 && getItem() != null) {
                    busy.set(true);
                    FileUtil.openFile(new File(getItem().getPath()), stage);
                    busy.set(false);
                }
            });
        }

        @Override
        protected void updateItem(RecommendedFile item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                setGraphic(new RecommendedFileListCellGraphic(this));
            } else {
                setGraphic(null);
            }
        }
    }

}
