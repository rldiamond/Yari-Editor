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

package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import components.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import table.DecisionTableService;
import validation.ValidateEvent;

public class GeneralToolView extends StackPane {

    private final DecisionTableService decisionTableService = DecisionTableService.getService();
    private final JFXTextField tableNameField = new JFXTextField();
    private final JFXTextField tableDescriptionField = new JFXTextField();
    private final JFXTextField ruleNameField = new JFXTextField();

    public GeneralToolView() {
        setPadding(new Insets(20, 20, 20, 20));

        Card card = new Card("General Table Settings");

        VBox cardContent = new VBox(25);

        //table name
        Label tableNameLabel = new Label("Table Name");
        tableNameLabel.setPrefWidth(150);
        tableNameLabel.setAlignment(Pos.CENTER_RIGHT);
        tableNameField.setPrefWidth(350);
        tableNameField.focusedProperty().addListener((obs, ov, focused) -> {
            if (!focused) {
                fireEvent(new ValidateEvent());
            }
        });
        tableNameField.setText(decisionTableService.getDecisionTable().getTableName());
        HBox tableNameContainer = new HBox(10);
        tableNameContainer.setAlignment(Pos.CENTER_LEFT);
        tableNameContainer.getChildren().setAll(tableNameLabel, tableNameField);

        //table description
        Label tableDescriptionLabel = new Label("Table Description");
        tableDescriptionLabel.setPrefWidth(150);
        tableDescriptionLabel.setAlignment(Pos.CENTER_RIGHT);
        tableDescriptionField.setPrefWidth(350);
        tableDescriptionField.focusedProperty().addListener((obs, ov, focused) -> {
            if (!focused) {
                fireEvent(new ValidateEvent());
            }
        });
        tableDescriptionField.setText(decisionTableService.getDecisionTable().getTableDescription());
        HBox tableDescriptionContainer = new HBox(10);
        tableDescriptionContainer.setAlignment(Pos.CENTER_LEFT);
        tableDescriptionContainer.getChildren().setAll(tableDescriptionLabel, tableDescriptionField);

        //rule name
        Label ruleNameLabel = new Label("Rule Name");
        ruleNameLabel.setPrefWidth(150);
        ruleNameLabel.setAlignment(Pos.CENTER_RIGHT);
        ruleNameField.setPrefWidth(350);
        ruleNameField.setText(decisionTableService.getRuleName());
        HBox ruleNameContainer = new HBox(10);
        ruleNameContainer.setAlignment(Pos.CENTER_LEFT);
        ruleNameContainer.getChildren().setAll(ruleNameLabel, ruleNameField);


        cardContent.getChildren().setAll(tableNameContainer, tableDescriptionContainer, ruleNameContainer);

        card.setDisplayedContent(cardContent);

        //bottom buttons
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        JFXButton applyButton = new JFXButton();
        applyButton.setText("APPLY");
        applyButton.setButtonType(JFXButton.ButtonType.RAISED);
        applyButton.getStyleClass().add("button-flat-green");

        applyButton.setOnMouseClicked(e -> save());

        JFXButton resetButton = new JFXButton();
        resetButton.setText("RESET");
        resetButton.setButtonType(JFXButton.ButtonType.RAISED);
        resetButton.getStyleClass().add("button-flat-red");

        resetButton.setOnMouseClicked(e -> reset());

        buttonBar.getChildren().addAll(resetButton, applyButton);
        card.setFooterContent(buttonBar);

        getChildren().setAll(card);
    }

    private void reset() {
        tableNameField.setText(decisionTableService.getDecisionTable().getTableName());
        tableDescriptionField.setText(decisionTableService.getDecisionTable().getTableDescription());
        ruleNameField.setText(decisionTableService.getRuleName());
        fireEvent(new ValidateEvent());
    }

    private void save() {
        decisionTableService.getDecisionTable().setName(tableNameField.getText());
        decisionTableService.getDecisionTable().setDescription(tableDescriptionField.getText());
        decisionTableService.setRuleName(ruleNameField.getText());
        fireEvent(new ValidateEvent());
    }
}
