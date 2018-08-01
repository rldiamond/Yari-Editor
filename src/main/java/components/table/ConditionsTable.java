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

import validation.UpdateEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.yari.core.table.Condition;
import objects.ComparatorType;
import objects.DataType;
import utilities.TableUtil;

import java.util.List;

public class ConditionsTable extends YariTable<Condition> {

    private static final DataFormat CONDITION_EDITOR = new DataFormat("application/x-java-serialized-object-condition-editor");

    public ConditionsTable() {
        setPlaceholder(new Label("Add one or more conditions to continue"));

        setRowFactory(tv -> {
            TableRow<Condition> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(CONDITION_EDITOR, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(CONDITION_EDITOR)) {
                    if (row.getIndex() != ((Integer) db.getContent(CONDITION_EDITOR)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragEntered(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(CONDITION_EDITOR)) {
                    row.getStyleClass().add("drag");
                    event.consume();
                }
            });

            row.setOnDragExited(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(CONDITION_EDITOR)) {
                    row.getStyleClass().remove("drag");
                    event.consume();
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(CONDITION_EDITOR)) {
                    int draggedIndex = (Integer) db.getContent(CONDITION_EDITOR);
                    Condition draggedRow = getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedRow);

                    event.setDropCompleted(true);
                    getSelectionModel().select(dropIndex);

                    TableUtil.reorderConditions(draggedIndex, dropIndex);

                    event.consume();
                    fireEvent(new UpdateEvent());
                }
            });

            return row;
        });
    }

    @Override
    protected List<TableColumn<Condition, ?>> buildColumns() {

        TableColumn<Condition, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getName() == null ? "ENTER A NAME" : cellData.getValue().getName();
            return new SimpleStringProperty(content);
        });
        nameCol.setCellFactory(c -> new EditableTextFieldCell<>());
        nameCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setName(newContent);
        });

        TableColumn<Condition, String> methodNameCol = new TableColumn<>("METHOD NAME");
        methodNameCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getMethodName() == null ? "ENTER A METHOD NAME" : cellData.getValue().getMethodName();
            return new SimpleStringProperty(content);
        });
        methodNameCol.setCellFactory(c -> new EditableTextFieldCell<>());
        methodNameCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setMethodName(newContent);
        });

        TableColumn<Condition, String> dataTypeCol = new TableColumn<>("DATA TYPE");
        dataTypeCol.setCellFactory(c -> new EditableComboBoxCell<>(DataType.getFXCompatibleList()));
        dataTypeCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getDataType() == null ? "SELECT DATA TYPE" : cellData.getValue().getDataType();
            return new SimpleStringProperty(content);
        });
        dataTypeCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setDataType(newContent);
            fireEvent(new UpdateEvent());
        });

        TableColumn<Condition, String> comparatorCol = new TableColumn<>("COMPARATOR");
        comparatorCol.setCellFactory(c -> new EditableComboBoxCell<>(ComparatorType.getFXCompatibleList()));
        comparatorCol.setCellValueFactory(cellData -> {
            String content = cellData.getValue().getDataType() == null ? "SELECT COMPARATOR" : cellData.getValue().getComparator();
            return new SimpleStringProperty(content);
        });
        comparatorCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setComparator(newContent);
            fireEvent(new UpdateEvent());
        });


        return List.of(nameCol, methodNameCol, dataTypeCol, comparatorCol);
    }
}
