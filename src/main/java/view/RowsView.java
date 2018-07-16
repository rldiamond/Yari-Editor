/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package view;

import com.jfoenix.controls.JFXButton;
import components.Card;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.yari.core.table.Row;
import view.table.RowTable;


public class RowsView extends StackPane {

    public ObservableList<Row> rowsList = RootLayoutFactory.getInstance().getRowsList();
    private RowTable rowTable = new RowTable(RootLayoutFactory.getInstance().getConditionsList(), RootLayoutFactory.getInstance().getActionsList());

    public RowsView() {
        setPadding(new Insets(20, 20, 20, 20));
        Card tableCard = new Card("Rows Editor");
        getChildren().setAll(tableCard);

        //table
        rowTable.setItems(rowsList);
        tableCard.setDisplayedContent(rowTable);

        ContextMenu contextMenu = new ContextMenu();
        var addMenuItem = new MenuItem("ADD");
        addMenuItem.setOnAction(e -> addRow(null));
        var removeMenuItem = new MenuItem("REMOVE");
        removeMenuItem.setOnAction(e -> removeRow(null));
        contextMenu.getItems().setAll(addMenuItem, removeMenuItem);
        rowTable.setContextMenu(contextMenu);

        //buttons
        JFXButton addAction = new JFXButton();
        addAction.setOnMouseClicked(this::addRow);
        addAction.setText("ADD");
        addAction.getStyleClass().add("button-flat-green");
        Tooltip.install(addAction, new Tooltip("Add new row"));

        JFXButton removeAction = new JFXButton();
        removeAction.disableProperty().bind(rowTable.getSelectionModel().selectedItemProperty().isNull());
        removeAction.setOnMouseClicked(this::removeRow);
        removeAction.setText("REMOVE");
        removeAction.getStyleClass().add("button-flat-red");
        Tooltip.install(removeAction, new Tooltip("Remove selected row"));

        HBox buttonWrapper = new HBox(5);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonWrapper.getChildren().setAll(removeAction, addAction);
        tableCard.setBottomContent(buttonWrapper);
    }

    private void addRow(MouseEvent me) {
        rowsList.add(new Row());
    }

    private void removeRow(MouseEvent mouseEvent) {
        Row selectedItem = rowTable.getSelectionModel().getSelectedItem();
        if (rowsList.contains(selectedItem)) {
            rowsList.remove(selectedItem);
        }
    }
}
