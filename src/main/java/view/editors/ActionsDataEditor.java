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
import components.table.ActionsTable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.yari.core.table.Action;
import utilities.FXUtil;
import view.RootLayoutFactory;

import java.util.Collections;


public class ActionsDataEditor extends StackPane implements DataEditor {

    private ObservableList<Action> actionList = RootLayoutFactory.getInstance().getActionsList();
    private ActionsTable actionsTable = new ActionsTable();

    public ActionsDataEditor() {
        setPadding(new Insets(20, 20, 20, 20));
        Card tableCard = new Card("Actions Editor");
        getChildren().setAll(tableCard);

        //table
        actionsTable.setItems(actionList);
        tableCard.setDisplayedContent(actionsTable);

        ContextMenu contextMenu = new ContextMenu();
        var addMenuItem = new MenuItem("ADD");
        addMenuItem.setOnAction(a -> addNewRow());
        var removeMenuItem = new MenuItem("REMOVE");
        removeMenuItem.setOnAction(a -> removeSelectedRow());
        contextMenu.getItems().setAll(addMenuItem, removeMenuItem);
        actionsTable.setContextMenu(contextMenu);

        //buttons
        JFXButton addAction = new JFXButton();
        addAction.setOnMouseClicked(a -> addNewRow());
        addAction.setText("ADD");
        addAction.getStyleClass().add("button-flat-green");
        Tooltip.install(addAction, new Tooltip("Add new action"));

        JFXButton removeAction = new JFXButton();
        removeAction.disableProperty().bind(actionsTable.getSelectionModel().selectedItemProperty().isNull());
        removeAction.setOnMouseClicked(me -> removeSelectedRow());
        removeAction.setText("REMOVE");
        removeAction.getStyleClass().add("button-flat-red");
        Tooltip.install(removeAction, new Tooltip("Remove selected action"));

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
        if (actionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Action selectedItem = actionsTable.getSelectionModel().getSelectedItem();
        if (actionList.contains(selectedItem)) {
            actionList.remove(selectedItem);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addNewRow() {
        actionList.add(new Action());
        FXUtil.runOnFXThread(() -> {
            actionsTable.requestFocus();
            actionsTable.getSelectionModel().selectLast();
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowUp() {
        if (actionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = actionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            Collections.swap(actionsTable.getItems(), selectedIndex, selectedIndex - 1);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowDown() {
        if (actionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = actionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < actionsTable.getItems().size() - 1) {
            Collections.swap(actionsTable.getItems(), selectedIndex, selectedIndex + 1);
        }
    }
}
