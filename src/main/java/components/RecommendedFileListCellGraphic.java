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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class RecommendedFileListCellGraphic extends StackPane {

    public RecommendedFileListCellGraphic(RecommendedFileListView.FileListCell fileListCell) {
        getStyleClass().add("fileCellGraphic");
        setPrefHeight(35);
        setMinHeight(35);
        setMaxHeight(35);
        Label name = new Label();
        final String nameText = formatName(fileListCell.getItem().getName());
        name.setText(nameText);
        Label path = new Label();
        path.setStyle("-fx-font-size: 10px");
        final String pathText = formatPath(fileListCell.getItem().getPath());
        path.setText(pathText);
        Tooltip.install(this, new Tooltip("Double-Click to open file " + nameText));
        VBox stackem = new VBox(name, path);
        stackem.setSpacing(2);
        getChildren().add(stackem);
        setAlignment(Pos.CENTER);
    }

    private String formatName(String string) {
        if (string.endsWith(".xml")) {
            return string.substring(0, string.length() - 4);
        } else {
            return string;
        }
    }

    private String formatPath(String string) {
        int maxChars = 45;
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
