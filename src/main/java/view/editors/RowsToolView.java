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
package view.editors;

import com.jfoenix.controls.JFXButton;
import components.Card;
import components.table.RowTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.yari.core.table.TableRow;
import table.DecisionTableService;
import utilities.FXUtil;

import java.util.Collections;

/**
 * Display a table of {@link Row} to allow for creation of additional rows,
 * editing available rows, and removing rows.
 */
public class RowsToolView extends StackPane implements DataEditor {

    private final DecisionTableService decisionTableService = DecisionTableService.getService();
    private RowTable rowTable = new RowTable();

    /**
     * Construct a RowsToolView with default settings.
     */
    public RowsToolView() {
        setPadding(new Insets(20, 20, 20, 20));
        Card tableCard = new Card("Rows Editor");
        super.getChildren().setAll(tableCard);

        //table
        rowTable.setItems(decisionTableService.getRows());
        tableCard.setDisplayedContent(rowTable);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("ADD");
        addMenuItem.setOnAction(a -> addNewRow());
        MenuItem removeMenuItem = new MenuItem("REMOVE");
        removeMenuItem.setOnAction(a -> removeSelectedRow());
        contextMenu.getItems().setAll(addMenuItem, removeMenuItem);
        rowTable.setContextMenu(contextMenu);

        //buttons
        JFXButton addAction = new JFXButton();
        addAction.setOnMouseClicked(me -> addNewRow());
        addAction.setText("ADD");
        addAction.getStyleClass().add("button-flat-green");
        Tooltip.install(addAction, new Tooltip("Add new row"));

        JFXButton removeAction = new JFXButton();
        removeAction.disableProperty().bind(rowTable.getSelectionModel().selectedItemProperty().isNull());
        removeAction.setOnMouseClicked(me -> removeSelectedRow());
        removeAction.setText("REMOVE");
        removeAction.getStyleClass().add("button-flat-red");
        Tooltip.install(removeAction, new Tooltip("Remove selected row"));

        HBox buttonWrapper = new HBox(5);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonWrapper.getChildren().setAll(removeAction, addAction);
        tableCard.setFooterContent(buttonWrapper);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeSelectedRow() {
        if (rowTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        TableRow selectedItem = rowTable.getSelectionModel().getSelectedItem();
        decisionTableService.getRows().remove(selectedItem);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addNewRow() {
        decisionTableService.getRows().add(new TableRow());
        FXUtil.runOnFXThread(() -> {
            rowTable.requestFocus();
            rowTable.getSelectionModel().selectLast();
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowUp() {
        if (rowTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = rowTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            Collections.swap(rowTable.getItems(), selectedIndex, selectedIndex - 1);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowDown() {
        if (rowTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = rowTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < rowTable.getItems().size() - 1) {
            Collections.swap(rowTable.getItems(), selectedIndex, selectedIndex + 1);
        }
    }
}
