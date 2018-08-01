/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package settings;

import com.jfoenix.controls.JFXButton;
import components.Card;
import components.EnumComboBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import objects.Theme;
import utilities.SettingsUtil;
import validation.ValidationType;

public class SettingsView extends Card {

    private Settings settings;

    private EnumComboBox<ValidationType> validationTypeComboBox;
    private EnumComboBox<Theme> themeComboBox;
    private CheckBox automaticSavingCheckBox;
    private TextField projectDirectoryField;

    public SettingsView(Settings settings) {
        super("Settings");
        this.settings = settings;

        //view settings
        VBox settingsContainer = new VBox(10);
        settingsContainer.setPrefSize(550, 300);
        settingsContainer.setPadding(new Insets(5, 5, 5, 5));

        //build the view
        //validation settings
        Label validationLabel = new Label("Validation:");
        validationLabel.setPrefWidth(75);
        validationTypeComboBox = new EnumComboBox<>(ValidationType.class);
        HBox validationSettings = new HBox(validationLabel, validationTypeComboBox);
        validationSettings.setAlignment(Pos.CENTER_LEFT);

        //theme settings
        Label themeLabel = new Label("Theme:");
        themeLabel.setPrefWidth(75);
        themeComboBox = new EnumComboBox<>(Theme.class);
        HBox themeSettings = new HBox(themeLabel, themeComboBox);
        themeSettings.setAlignment(Pos.CENTER_LEFT);

        //automatic saving
        automaticSavingCheckBox = new CheckBox("Automatic Saving");
        HBox automaticSavingSettings = new HBox(automaticSavingCheckBox);
        automaticSavingSettings.setAlignment(Pos.CENTER_LEFT);
        automaticSavingSettings.setDisable(true); //Note: Future feature

        //projects directory
        Label projectDirectoryLabel = new Label("Project Directory:");
        projectDirectoryLabel.setPrefWidth(120);
        projectDirectoryField = new TextField();
        projectDirectoryField.setEditable(false);
        Pane browsePane = new Pane();
        browsePane.setPrefSize(16, 16);
        browsePane.setMaxSize(16,16);
        browsePane.setId("browse");
        HBox projectsDirectorySettings = new HBox(projectDirectoryLabel, projectDirectoryField, browsePane);
        projectsDirectorySettings.setAlignment(Pos.CENTER_LEFT);
        projectsDirectorySettings.setSpacing(8);
        projectsDirectorySettings.setDisable(true); //Note: Future feature

        settingsContainer.getChildren().addAll(validationSettings, themeSettings, projectsDirectorySettings, automaticSavingSettings);

        //call to initially populate the fields
        resetSettings();

        setDisplayedContent(settingsContainer);

        //footer (buttons)
        //buttons
        JFXButton save = new JFXButton();
        save.setOnMouseClicked(a -> saveSettings());
        save.setText("OKAY");
        save.getStyleClass().add("button-flat-gray");
        Tooltip.install(save, new Tooltip("Save and Close"));

        JFXButton discard = new JFXButton();
        discard.setOnMouseClicked(me -> resetSettings());
        discard.setText("DISCARD");
        discard.getStyleClass().add("button-flat-red");
        Tooltip.install(discard, new Tooltip("Discard"));

        HBox buttonWrapper = new HBox(5);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonWrapper.getChildren().setAll(discard, save);
        setFooterContent(buttonWrapper);
    }

    private void saveSettings() {
        Settings updatedSettings = new Settings();
        updatedSettings.setAutoSave(automaticSavingCheckBox.isSelected());
        updatedSettings.setProjectDirectory(projectDirectoryField.getText());
        updatedSettings.setTheme(themeComboBox.getValue());
        updatedSettings.setValidationType(validationTypeComboBox.getValue());

        SettingsUtil.saveSettings(updatedSettings);
        settings = updatedSettings;
    }

    private void resetSettings() {
        validationTypeComboBox.getSelectionModel().select(settings.getValidationType());
        themeComboBox.getSelectionModel().select(settings.getTheme());
        automaticSavingCheckBox.setSelected(settings.isAutoSave());
        projectDirectoryField.setText(settings.getProjectDirectory());
    }

}
