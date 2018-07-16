/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package view.table;

import components.EditableTextFieldCell;
import components.YariTable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.Row;
import utilities.TextUtil;

import java.util.List;

public class RowTable extends YariTable<Row> {

    public RowTable(ObservableList<Condition> conditions, ObservableList<Action> actions) {
        setPlaceholder(new Label("Add one or more rows to continue"));
    }

    @Override
    protected List<TableColumn<Row, ?>> buildColumns() {

        //nested columns
        TableColumn conditionsCol = new TableColumn("CONDITIONS");
        TableColumn actionsCol = new TableColumn("ACTIONS");
        TableColumn<Row, Integer> rowNumCol = new TableColumn("#");
        rowNumCol.setPrefWidth(45);
        rowNumCol.setMaxWidth(45);
        rowNumCol.setMinWidth(45);
        rowNumCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRowNumber()).asObject());

        //conditions
        var columnNum = 0;
        for (Condition condition : conditions) {

            TableColumn conditionCol = new TableColumn(TextUtil.toTitleCase(condition.getName() + " (" + condition.getDataType() + ", " + condition.getComparator() + ")"));
            final int column = columnNum;
            // Assign data to a column.
            conditionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Row, String>, ObservableValue<String>>) param -> {
                Row r = param.getValue();
                List<String> rowValues = r.getValues();
                if (column >= rowValues.size()) {
                    return new SimpleStringProperty("UNDEFINED");
                }
                return new SimpleStringProperty(rowValues.get(column));
            });
            //edit
            if (condition.getDataType().equalsIgnoreCase("boolean")) {
                conditionCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), boolOptions));
            } else {
                conditionCol.setCellFactory(c -> new EditableTextFieldCell<>());
            }
            conditionCol.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Row, String>>) t -> {
                String s = t.getNewValue().trim();
                Row row = t.getRowValue();
                for (int i = row.getValues().size(); i <= column; i++) {
                    row.getValues().add("");
                }
                row.getValues().set(column, s);
                refresh();
            });
            conditionsCol.getColumns().addAll(conditionCol);
            columnNum++;
        }

        //actions
        columnNum = 0;
        // Create the action columns.
        for (Action action : actions) {
            TableColumn col = new TableColumn(TextUtil.toTitleCase(action.getName()) + " (" + action.getDataType() + ")");

            final int column = columnNum;
            // Assign data to a column.
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Row, String>, ObservableValue<String>>) param -> {
                Row r = param.getValue();
                List<String> rowValues = r.getResults();
                if (column >= rowValues.size()) {
                    return new SimpleStringProperty("UNDEFINED");
                }
                return new SimpleStringProperty(rowValues.get(column));
            });

            // Enables table editing
            if (action.getDataType().equalsIgnoreCase("boolean")) {
                col.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), boolOptions));
            } else {
                col.setCellFactory(c -> new EditableTextFieldCell<>());
            }

            col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Row, String>>) t -> {
                        String s = t.getNewValue().trim();
                        Row row = t.getRowValue();
                        for (int i = row.getResults().size(); i <= column; i++) {
                            row.getResults().add("");
                        }
                        row.getResults().set(column, s);
                    }
            );
            actionsCol.getColumns().addAll(col);
            columnNum++;
        }


        return List.of(rowNumCol, conditionsCol, actionsCol);
    }
}
