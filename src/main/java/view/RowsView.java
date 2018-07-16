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
import javafx.collections.ListChangeListener;
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

/**
 * Display a table of {@link Row} to allow for creation of additional rows, editing available rows, and removing rows.
 */
public class RowsView extends StackPane {

    private ObservableList<Row> rowsList = RootLayoutFactory.getInstance().getRowsList();
    private RowTable rowTable = new RowTable();

    /**
     * Construct a RowsView with default settings.
     */
    public RowsView() {
        setPadding(new Insets(20, 20, 20, 20));
        Card tableCard = new Card("Rows Editor");
        getChildren().setAll(tableCard);

        //table
        rowTable.setItems(rowsList);
        tableCard.setDisplayedContent(rowTable);

        //renumber rows on change
        rowsList.addListener((ListChangeListener.Change<? extends Row> c) -> {
            while (c.next()) {
                renumberRows();
            }
        });

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

    /**
     * Add a {@link Row} to the decision table.
     *
     * @param mouseEvent the event calling this method.
     */
    private void addRow(MouseEvent mouseEvent) {
        rowsList.add(new Row());
    }

    /**
     * Remove a {@link Row} from the decision table.
     *
     * @param mouseEvent the event calling this method.
     */
    private void removeRow(MouseEvent mouseEvent) {
        Row selectedItem = rowTable.getSelectionModel().getSelectedItem();
        if (rowsList.contains(selectedItem)) {
            rowsList.remove(selectedItem);
        }
    }

    /**
     * Rows need to be renumbered when added, removed, or re-arranged.
     */
    private void renumberRows() {
        var rowNumber = 0;
        for (Row row : RootLayoutFactory.getInstance().getRowsList()) {
            row.setRowNumber(rowNumber++);
        }
    }
}
