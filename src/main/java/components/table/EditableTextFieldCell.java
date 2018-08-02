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

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import utilities.FXUtil;
import validation.ValidateEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of TableCell that provides extra features to make mass data editing more familiar.
 * Adds tab to next column, enter to commit, escape to cancel, unfocus to commit, and fires an update event
 * on commit to validate the table after every change.
 *
 * @param <T>
 */
public class EditableTextFieldCell<T extends Object> extends TableCell<T, String> {

    private TextField textField;

    /**
     * Construct the cell with default settings.
     */
    public EditableTextFieldCell() {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            FXUtil.runOnFXThread(() -> {
                textField.requestFocus();
                textField.selectAll();
            });
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
        fireEvent(new ValidateEvent());
    }

    /**
     * Creates the text field and applies listeners to it which allow for our custom editing.
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()
                * 2);

        textField.focusedProperty().addListener(
                (arg0, arg1, arg2) -> {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                });

        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getText());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            } else if (t.getCode() == KeyCode.TAB) {
                commitEdit(textField.getText());
                TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                if (nextColumn != null) {
                    getTableView().edit(getTableRow().getIndex(), nextColumn);
                }

            }
        });
    }

    /**
     * Convenience method to return a non-null String from the item.
     *
     * @return a non-null String from the item.
     */
    private String getString() {
        return getItem() == null ? "" : getItem();
    }

    /**
     * Returns the next column forward or backward, depending on the supplied value.
     *
     * @param forward true to get the next column forward (to the right), or false to get the next column backward (to
     *                the left).
     * @return the next column forward or backward, depending on the supplied value.
     */
    private TableColumn<T, ?> getNextColumn(boolean forward) {
        List<TableColumn<T, ?>> columns = new ArrayList<>();
        for (TableColumn<T, ?> column : getTableView().getColumns()) {
            columns.addAll(getLeaves(column));
        }
        // There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int nextIndex = columns.indexOf(getTableColumn());
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return columns.get(nextIndex);
    }

    /**
     * Used in the getNextColumn method to provide leaves of the column
     *
     * @param root the column to get the leaves from
     * @return all (editable) leaves of the supplied table column.
     */
    private List<TableColumn<T, ?>> getLeaves(TableColumn<T, ?> root) {
        List<TableColumn<T, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            // We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (TableColumn<T, ?> column : root.getColumns()) {
                columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }
}
