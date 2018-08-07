/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.yari.core.table.DecisionTable;
import utilities.DecisionTableService;
import utilities.ThemeUtil;
import components.table.RowTable;

public class TablePrintView extends StackPane {

    public TablePrintView(DecisionTable decisionTable){
        setPrefSize(1920, 1080);

        Label tableNameLabel = new Label("Table Name:");
        tableNameLabel.setPrefWidth(150);

        Label tableName = new Label(decisionTable.getTableName());
        HBox nameWrapper = new HBox(tableNameLabel, tableName);


        Label tableDescriptionLabel = new Label("Table Description");
        tableDescriptionLabel.setPrefWidth(150);
        Label tableDescription = new Label(decisionTable.getTableDescription());
        HBox descriptionWrapper = new HBox(tableDescriptionLabel, tableDescription);

        RowTable rowTable = new RowTable();
        rowTable.setItems(DecisionTableService.getService().getRows());
        VBox.setVgrow(rowTable, Priority.ALWAYS);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(nameWrapper, descriptionWrapper, rowTable);
        getChildren().setAll(vBox);

        getStylesheets().setAll(ThemeUtil.getActiveTheme().getCss());
    }

}
