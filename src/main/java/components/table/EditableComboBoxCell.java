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

package components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import validation.ValidateEvent;

import java.util.List;

/**
 * Extension of TableCell that provides additional features to make data editing simpler. Provides a ComboBox populated
 * with the supplied String contents in the constructor. Any time a change is made to the ComboBox, commitEdit is called
 * and an event to validate the workspace is fired.
 *
 * @param <T>
 */
public class EditableComboBoxCell<T extends Object> extends TableCell<T, String> {

    private ComboBox<String> comboBox;
    private ObservableList<String> contents = FXCollections.observableArrayList();

    /**
     * Construct the cell with default settings and populate the ComboBox with the supplied contents.
     *
     * @param contents String contents to populate the ComboBox with.
     */
    public EditableComboBoxCell(List<String> contents) {
        this.contents.addAll(contents);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBox);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
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
                setText(getString());
                setGraphic(comboBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    /**
     * Creates the ComboBox and applies listeners to it which allow for our custom editing.
     */
    private void createComboBox() {
        comboBox = new ComboBox<>();
        comboBox.setItems(contents);
        comboBox.valueProperty().set(getString());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction(e -> commitEdit(comboBox.getSelectionModel().getSelectedItem()));
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, ov, selected) -> commitEdit(selected));
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
     * Convenience method to return a non-null String from the item.
     *
     * @return a non-null String from the item.
     */
    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}

