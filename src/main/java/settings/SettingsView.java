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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import objects.Theme;
import utilities.SettingsUtil;
import validation.ValidationType;

import java.io.File;

public class SettingsView extends Card {

    private Settings settings;

    private EnumComboBox<ValidationType> validationTypeComboBox;
    private EnumComboBox<Theme> themeComboBox;
    private TextField projectDirectoryField;
    private JFXButton okayButton;

    public SettingsView(Settings settings, Stage stage) {
        super("Settings");
        this.settings = settings;
        setId("settings");

        //view settings
        VBox settingsContainer = new VBox(10);
        settingsContainer.setPrefSize(550, 300);
        settingsContainer.setPadding(new Insets(5, 5, 5, 5));

        //build the view
        //validation settings
        Label validationLabel = new Label("Validation:");
        validationLabel.setPrefWidth(75);
        validationTypeComboBox = new EnumComboBox<>(ValidationType.class);
        validationTypeComboBox.setPrettyPrint(true);
        HBox validationSettings = new HBox(validationLabel, validationTypeComboBox);
        validationSettings.setAlignment(Pos.CENTER_LEFT);
        Tooltip.install(validationSettings, new Tooltip("Select a validation method."));

        //theme settings
        Label themeLabel = new Label("Theme:");
        themeLabel.setPrefWidth(75);
        themeComboBox = new EnumComboBox<>(Theme.class);
        themeComboBox.setPrettyPrint(true);
        HBox themeSettings = new HBox(themeLabel, themeComboBox);
        themeSettings.setAlignment(Pos.CENTER_LEFT);
        Tooltip.install(themeSettings, new Tooltip("Select a theme."));

        //projects directory
        Label projectDirectoryLabel = new Label("Project Directory:");
        projectDirectoryLabel.setPrefWidth(120);
        projectDirectoryField = new TextField();
        projectDirectoryField.setEditable(false);
        projectDirectoryField.setPrefWidth(USE_COMPUTED_SIZE);
        HBox.setHgrow(projectDirectoryField, Priority.ALWAYS);
        Pane browsePane = new Pane();
        browsePane.setPrefSize(16, 16);
        browsePane.setMaxSize(16, 16);
        browsePane.setId("browse");
        browsePane.setOnMouseClicked(me -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(stage.getOwner());
            if (file == null) {
                projectDirectoryField.setText("Select a directory.");
            } else {
                projectDirectoryField.setText(file.getPath());
            }
        });

        HBox projectsDirectorySettings = new HBox(projectDirectoryLabel, browsePane, projectDirectoryField);
        projectsDirectorySettings.setAlignment(Pos.CENTER_LEFT);
        projectsDirectorySettings.setSpacing(3);
        Tooltip.install(projectsDirectorySettings, new Tooltip("Select a project directory."));

        settingsContainer.getChildren().addAll(validationSettings, themeSettings, projectsDirectorySettings);

        //call to initially populate the fields
        resetSettings();

        setDisplayedContent(settingsContainer);

        //footer (buttons)
        //buttons
        okayButton = new JFXButton();
        okayButton.setOnMouseClicked(a -> {
            saveSettings();
            stage.close();
        });
        okayButton.setText("OKAY");
        okayButton.getStyleClass().add("button-flat-gray");
        Tooltip.install(okayButton, new Tooltip("Save and close"));

        JFXButton discard = new JFXButton();
        discard.setOnMouseClicked(me -> {
            resetSettings();
            stage.close();
        });
        discard.setText("CANCEL");
        discard.getStyleClass().add("button-flat-red");
        Tooltip.install(discard, new Tooltip("Discard changes and close"));

        HBox buttonWrapper = new HBox(5);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonWrapper.getChildren().setAll(discard, okayButton);
        setFooterContent(buttonWrapper);
    }

    private void saveSettings() {
        Settings updatedSettings = new Settings();
        if (!projectDirectoryField.getText().equalsIgnoreCase("Select a directory.")) {
            updatedSettings.setProjectDirectory(projectDirectoryField.getText());
        }
        updatedSettings.setTheme(themeComboBox.getValue());
        updatedSettings.setValidationType(validationTypeComboBox.getValue());

        SettingsUtil.saveSettings(updatedSettings);
        settings = updatedSettings;
    }

    private void resetSettings() {
        validationTypeComboBox.getSelectionModel().select(settings.getValidationType());
        themeComboBox.getSelectionModel().select(settings.getTheme());
        projectDirectoryField.setText(settings.getProjectDirectory());
    }
}
