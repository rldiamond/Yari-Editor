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
import components.table.ConditionsTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.yari.core.table.TableCondition;
import table.DecisionTableService;
import utilities.FXUtil;
import validation.ValidateEvent;

import java.util.Collections;

public class ConditionsToolView extends StackPane implements DataEditor {

    private DecisionTableService decisionTableService = DecisionTableService.getService();
    private ConditionsTable conditionsTable = new ConditionsTable();

    public ConditionsToolView() {
        setPadding(new Insets(20, 20, 20, 20));
        Card tableCard = new Card("Conditions Editor");
        super.getChildren().setAll(tableCard);

        //table
        conditionsTable.setItems(decisionTableService.getConditions());
        tableCard.setDisplayedContent(conditionsTable);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("ADD");
        addMenuItem.setOnAction(a -> addNewRow());
        MenuItem removeMenuItem = new MenuItem("REMOVE");
        removeMenuItem.setOnAction(a -> removeSelectedRow());
        contextMenu.getItems().setAll(addMenuItem, removeMenuItem);
        conditionsTable.setContextMenu(contextMenu);

        //buttons
        JFXButton addAction = new JFXButton();
        addAction.setOnMouseClicked(a -> addNewRow());
        addAction.setText("ADD");
        addAction.getStyleClass().add("button-flat-green");
        Tooltip.install(addAction, new Tooltip("Add new condition"));

        JFXButton removeAction = new JFXButton();
        removeAction.disableProperty().bind(conditionsTable.getSelectionModel().selectedItemProperty().isNull());
        removeAction.setOnMouseClicked(me -> removeSelectedRow());
        removeAction.setText("REMOVE");
        removeAction.getStyleClass().add("button-flat-red");
        Tooltip.install(removeAction, new Tooltip("Remove selected condition"));

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
        if (conditionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        TableCondition selectedItem = conditionsTable.getSelectionModel().getSelectedItem();
        decisionTableService.getConditions().remove(selectedItem);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addNewRow() {
        decisionTableService.getConditions().add(new TableCondition());
        FXUtil.runOnFXThread(() -> {
            conditionsTable.requestFocus();
            conditionsTable.getSelectionModel().selectLast();
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowUp() {
        if (conditionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = conditionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            Collections.swap(conditionsTable.getItems(), selectedIndex, selectedIndex - 1);
            decisionTableService.reorderConditions(selectedIndex, selectedIndex - 1);
            fireEvent(new ValidateEvent());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void moveRowDown() {
        if (conditionsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectedIndex = conditionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < conditionsTable.getItems().size() - 1) {
            Collections.swap(conditionsTable.getItems(), selectedIndex, selectedIndex + 1);
            decisionTableService.reorderConditions(selectedIndex, selectedIndex + 1);
            fireEvent(new ValidateEvent());
        }
    }
}
