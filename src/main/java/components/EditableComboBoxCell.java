package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

import java.util.List;

public class EditableComboBoxCell<T extends Object> extends TableCell<T, String> {

    private ComboBox<String> comboBox;
    private ObservableList<String> contents = FXCollections.observableArrayList();

    public EditableComboBoxCell(List<String> contents) {
        this.contents.addAll(contents);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBox);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        setGraphic(null);
    }

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

    private void createComboBox() {
        comboBox = new ComboBox<>();
        comboBox.setItems(contents);
        comboBox.valueProperty().set(getString());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, ov, selected) -> {
            commitEdit(selected);
        });
    }

    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
        fireEvent(new UpdateEvent());
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}

