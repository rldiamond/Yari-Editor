/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
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

package view.table;

import components.EditableTextFieldCell;
import components.UpdateEvent;
import components.YariTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.converter.DefaultStringConverter;
import org.yari.core.table.Action;
import utilities.DecisionTableValidator;

import java.util.List;

public class ActionsTable extends YariTable<Action> {

    private static final DataFormat ACTION_EDITOR = new DataFormat("application/x-java-serialized-object-action-editor");

    public ActionsTable() {
        setPlaceholder(new Label("Add one or more actions to continue"));

        setRowFactory(tv -> {
            TableRow<Action> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(ACTION_EDITOR, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ACTION_EDITOR)) {
                    if (row.getIndex() != ((Integer) db.getContent(ACTION_EDITOR)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragEntered(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ACTION_EDITOR)) {
                    row.getStyleClass().add("drag");
                    event.consume();
                }
            });

            row.setOnDragExited(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ACTION_EDITOR)) {
                    row.getStyleClass().remove("drag");
                    event.consume();
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(ACTION_EDITOR)) {
                    int draggedIndex = (Integer) db.getContent(ACTION_EDITOR);
                    Action draggedRow = getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedRow);

                    event.setDropCompleted(true);
                    getSelectionModel().select(dropIndex);

                    DecisionTableValidator.reorderActions(draggedIndex, dropIndex);
                    event.consume();
                }
            });

            return row;
        });
    }

    @Override
    protected List<TableColumn<Action, ?>> buildColumns() {

        TableColumn<Action, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getName() == null ? "ENTER A NAME" : cellData.getValue().getName();
            return new SimpleStringProperty(content);
        });
        nameCol.setCellFactory(c -> new EditableTextFieldCell<>());
        nameCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setName(newContent);
        });

        TableColumn<Action, String> methodNameCol = new TableColumn<>("METHOD NAME");
        methodNameCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getMethodName() == null ? "ENTER A METHOD NAME" : cellData.getValue().getMethodName();
            return new SimpleStringProperty(content);
        });
        methodNameCol.setCellFactory(c -> new EditableTextFieldCell<>());
        methodNameCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setMethodname(newContent);
        });

        TableColumn<Action, String> dataTypeCol = new TableColumn<>("DATA TYPE");
        dataTypeCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), dataTypeValues));
        dataTypeCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getDataType() == null ? "SELECT DATA TYPE" : cellData.getValue().getDataType();
            return new SimpleStringProperty(content);
        });
        dataTypeCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setDatatype(newContent);
            fireEvent(new UpdateEvent());
        });

        return List.of(nameCol, methodNameCol, dataTypeCol);
    }
}
