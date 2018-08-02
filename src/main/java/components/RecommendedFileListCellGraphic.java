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

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import utilities.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom graphic for the RecommendedFile list cell.
 */
public class RecommendedFileListCellGraphic extends StackPane {

    RecommendedFileListCellGraphic(RecommendedFileListView.FileListCell fileListCell) {
        getStyleClass().add("fileCellGraphic");
        setPrefHeight(35);
        setMinHeight(35);
        setMaxHeight(35);
        Label name = new Label();
        final String nameText = formatName(fileListCell.getItem().getName());
        name.setStyle("-fx-font-weight: bold");
        name.setText(nameText);
        Label path = new Label();
        path.setStyle("-fx-font-size: 10px");
        final String pathText = formatPath(fileListCell.getItem().getPath());
        path.setText(pathText);
        VBox stackem = new VBox(name, path);
        Tooltip.install(this, new Tooltip("Double-Click to open file " + nameText));

        stackem.setSpacing(2);
        setAlignment(Pos.CENTER);

        //remove item
        StackPane removeButton = new StackPane();
        removeButton.setAlignment(Pos.TOP_RIGHT);
        Pane remove = new Pane();
        remove.setPrefSize(12, 12);
        remove.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        remove.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        remove.setId("closeButton");
        remove.setManaged(false);
        remove.setVisible(false);
        remove.setOnMouseClicked(me -> {
            SettingsUtil.removeRecommendedFile(fileListCell.getItem());
            fileListCell.getListView().getItems().remove(fileListCell.getItem());
        });
        Tooltip.install(remove, new Tooltip("Remove entry"));
        setOnMouseEntered(e -> {
            if (fileListCell.isSelected()) {
                remove.setManaged(true);
                remove.setVisible(true);
            }
        });
        setOnMouseExited(e -> {
            if (fileListCell.isSelected()) {
                remove.setVisible(false);
                remove.setManaged(false);
            }
        });
        removeButton.getChildren().add(remove);
        HBox.setHgrow(removeButton, Priority.ALWAYS);
        HBox wrappem = new HBox();
        wrappem.getChildren().addAll(stackem, removeButton);
        getChildren().add(wrappem);

    }

    private String formatName(String string) {
        if (string.endsWith(".xml")) {
            return string.substring(0, string.length() - 4);
        } else {
            return string;
        }
    }

    private String formatPath(String string) {
        int maxChars = 35;
        if (string.length() <= maxChars) {
            return string;
        }

        final String[] split = string.split("/");

        List<String> backwards = new ArrayList<>();

        int size = 0;
        for (int i = split.length - 1; i >= 0; i--) {
            if (size + split[i].length() <= maxChars) {
                size += split[i].length();
                backwards.add(split[i]);
            } else {
                break;
            }
        }

        StringBuilder path = new StringBuilder();
        for (int i = backwards.size() - 1; i >= 0; i--) {
            path.append("/").append(backwards.get(i));
        }


        return path.toString();

    }

}
