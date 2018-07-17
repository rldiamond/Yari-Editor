package view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.yari.core.table.DecisionTable;
import utilities.ThemeUtil;
import view.table.RowTable;

public class TablePrintView extends StackPane {

    public TablePrintView(){
        setPrefSize(1920, 1080);
        final DecisionTable decisionTable = RootLayoutFactory.getInstance().getDecisionTable();

        Label tableNameLabel = new Label("Table Name:");
        tableNameLabel.setPrefWidth(150);
        Label tableName = new Label(decisionTable.getTableName());
        HBox nameWrapper = new HBox(tableNameLabel, tableName);


        Label tableDescriptionLabel = new Label("Table Description");
        tableDescriptionLabel.setPrefWidth(150);
        Label tableDescription = new Label(decisionTable.getTableDescription());
        HBox descriptionWrapper = new HBox(tableDescriptionLabel, tableDescription);

        RowTable rowTable = new RowTable();
        rowTable.setItems(RootLayoutFactory.getInstance().getRowsList());
        VBox.setVgrow(rowTable, Priority.ALWAYS);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(nameWrapper, descriptionWrapper, rowTable);
        getChildren().setAll(vBox);

        getStylesheets().setAll(ThemeUtil.getActiveTheme().getCss());
    }

}
