/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

 /*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */
package components.table;

import java.util.Arrays;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import utilities.TextUtil;
import validation.ValidateEvent;

import java.util.List;
import javafx.scene.control.Tooltip;

public class RowTable extends YariTable<org.yari.core.table.TableRow> {

    private static final DataFormat ROW_EDITOR = new DataFormat("application/x-java-serialized-object");

    public RowTable() {
        setPlaceholder(new Label("Add one or more rows to continue"));

        setRowFactory(tv -> {
            TableRow<org.yari.core.table.TableRow> row = new TableRow<>();
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(ROW_EDITOR, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ROW_EDITOR) && row.getIndex() != (Integer) db.getContent(ROW_EDITOR)) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    event.consume();
                }
            });

            row.setOnDragEntered(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ROW_EDITOR)) {
                    row.getStyleClass().add("drag");
                    event.consume();
                }
            });

            row.setOnDragExited(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ROW_EDITOR)) {
                    row.getStyleClass().remove("drag");
                    event.consume();
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ROW_EDITOR)) {
                    int draggedIndex = (Integer) db.getContent(ROW_EDITOR);
                    org.yari.core.table.TableRow draggedRow = getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedRow);

                    event.setDropCompleted(true);
                    getSelectionModel().select(dropIndex);
                    event.consume();
                    fireEvent(new ValidateEvent());
                }
            });

            return row;
        });
    }

    @Override
    protected List<TableColumn<org.yari.core.table.TableRow, ?>> buildColumns() {
        //nested columns
        TableColumn conditionsCol = new TableColumn("CONDITIONS");
        TableColumn actionsCol = new TableColumn("ACTIONS");
        TableColumn<org.yari.core.table.TableRow, Integer> rowNumCol = new TableColumn("#");
        rowNumCol.setPrefWidth(45);
        rowNumCol.setMaxWidth(45);
        rowNumCol.setMinWidth(45);
        rowNumCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRowNumber()).asObject());

        //conditions
        int columnNum = 0;
        for (TableCondition condition : DECISION_TABLE_SERVICE.getConditions()) {
//            final String condTitle = TextUtil.toTitleCase(condition.getName() + " (" + condition.getDataType() + ", " + condition.getComparator() + ")");
//
//            Label condLabel = new Label(condTitle);
//            Tooltip.install(condLabel, new Tooltip(condTitle));

            TableColumn conditionCol = new TableColumn();
            conditionCol.setGraphic(new RowColumnHeaderGraphic(condition));

            final int column = columnNum;
            // Assign data to a column.
            conditionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<org.yari.core.table.TableRow, String>, ObservableValue<String>>) param -> {
                org.yari.core.table.TableRow r = param.getValue();
                List<String> rowValues = r.getValues();
                if (column >= rowValues.size()) {
                    return new SimpleStringProperty("UNDEFINED");
                }
                return new SimpleStringProperty(rowValues.get(column));
            });
            //edit
            if (condition.getDataType() != null && condition.getDataType().equalsIgnoreCase("boolean")) {
                conditionCol.setCellFactory(c -> new EditableComboBoxCell<>(BOOLEAN_OPTIONS));
            } else {
                conditionCol.setCellFactory(c -> new EditableTextFieldCell<>());
            }
            conditionCol.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<org.yari.core.table.TableRow, String>>) t -> {
                String s = t.getNewValue().trim();
                org.yari.core.table.TableRow row = t.getRowValue();
                for (int i = row.getValues().size(); i <= column; i++) {
                    row.getValues().add("");
                }
                row.getValues().set(column, s);

            });
            conditionsCol.getColumns().addAll(conditionCol);
            columnNum++;
        }

        //actions
        // Create the action columns.
        for (TableAction action : DECISION_TABLE_SERVICE.getActions()) {
//            final String actionTitle = TextUtil.toTitleCase(action.getName()) + " (" + action.getDataType() + ")";
//
//            Label actionLabel = new Label(actionTitle);
//            Tooltip.install(actionLabel, new Tooltip(actionTitle));

            TableColumn col = new TableColumn();
            col.setGraphic(new HoverableRowColumnHeaderGraphic(action));

            final int column = 0;
            // Assign data to a column.
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<org.yari.core.table.TableRow, String>, ObservableValue<String>>) param -> {
                org.yari.core.table.TableRow r = param.getValue();
                List<String> rowValues = r.getResults();
                if (column >= rowValues.size()) {
                    return new SimpleStringProperty("UNDEFINED");
                }
                return new SimpleStringProperty(rowValues.get(column));
            });

            // Enables table editing
            if (action.getDataType() != null && action.getDataType().equalsIgnoreCase("boolean")) {
                col.setCellFactory(c -> new EditableComboBoxCell<>(BOOLEAN_OPTIONS));
            } else {
                col.setCellFactory(c -> new EditableTextFieldCell<>());
            }

            col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<org.yari.core.table.TableRow, String>>) t -> {
                String s = t.getNewValue().trim();
                org.yari.core.table.TableRow row = t.getRowValue();
                for (int i = row.getResults().size(); i <= column; i++) {
                    row.getResults().add("");
                }
                row.getResults().set(column, s);
            }
            );
            actionsCol.getColumns().addAll(col);
            columnNum++;
        }

        return Arrays.asList(conditionsCol, actionsCol, rowNumCol);
    }
}
