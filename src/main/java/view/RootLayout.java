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

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import components.MenuOption;
import components.PopupMenuEntry;
import excel.ExcelImportResultsCard;
import excel.ExcelImporter;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.KeyboardShortcut;
import objects.ToolView;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.yari.core.table.DecisionTable;
import settings.SettingsView;
import utilities.*;
import validation.ValidateEvent;
import validation.ValidationService;
import validation.view.ValidationLogDialog;
import view.editors.ActionsToolView;
import view.editors.ConditionsToolView;
import view.editors.DataEditor;
import view.editors.RowsToolView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class RootLayout extends BorderPane {

    private final ValidationService validationService = ValidationService.getService();
    private final DecisionTableService decisionTableService = DecisionTableService.getService();
    private final ObservableList<MenuOption> menuOptions = FXCollections.observableArrayList();
    private final BooleanProperty loadingContent = new SimpleBooleanProperty(false);
    private final BooleanProperty controlsLock = new SimpleBooleanProperty(false);
    private final StackPane displayedContent = new StackPane();
    private final AnchorPane header = new AnchorPane();

    private Pane minimizeButton;
    private Pane maximizeButton;
    private JFXSnackbar toastBar;


    public RootLayout() {

        //when loadingContent, display a loading indicator
        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(30);
        loadingSpinner.visibleProperty().bind(loadingContent);
        loadingSpinner.managedProperty().bind(loadingContent);
        //blank pane to cover up any displayed content
        StackPane loadingPane = new StackPane();
        loadingPane.setStyle("-fx-background-color: white");
        FadeTransition fadePaneOut = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.OUT);
        FadeTransition fadePaneIn = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.IN);

        loadingContent.addListener((obs, ov, loading) -> {
            if (loading) {
                fadePaneOut.stop();
                loadingPane.setOpacity(0);
                loadingPane.setVisible(true);
                loadingPane.setManaged(true);
                fadePaneIn.play();
            } else {
                fadePaneIn.stop();
                fadePaneOut.play();
            }
        });

        //encapsulate the displayedContent so that the loadingSpinner may always appear on top.
        StackPane mainPanel = new StackPane();
        mainPanel.getChildren().setAll(displayedContent, loadingPane, loadingSpinner);

        toastBar = new JFXSnackbar(mainPanel);
        toastBar.setPrefWidth(350);

        //initial (minimal loading required)
        prepareOptions();
        prepareHeader();
        setCenter(mainPanel);

        addEventHandler(ValidateEvent.VALIDATE, e -> validationService.requestValidation());

        //change toast on valid
        validationService.validProperty().addListener((obs, ov, nv) -> {
            if (!ov && nv) {
                toastBar.close();
                ToastUtil.sendToast("Workspace now validates!");
            }
        });

        StackPane getStartedPane = new StackPane(new Label("Select an option to the left to get started."));
        displayedContent.getChildren().add(getStartedPane);
        fadePaneOut.play();

    }


    private void prepareHeader() {
        header.setId("header");

        //style it
        header.setPrefSize(USE_COMPUTED_SIZE, 45);
        header.setMaxSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);
        header.setMinSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);

        //branding
        HBox branding = new HBox(15);
        branding.setId("branding");
        branding.setPrefWidth(175);
        branding.setMaxWidth(USE_PREF_SIZE);
        branding.setMinWidth(USE_PREF_SIZE);
        branding.setAlignment(Pos.CENTER_LEFT);
        branding.setPadding(new Insets(5, 5, 5, 10));
        ImageView logo = new ImageView();
        logo.setImage(ThemeUtil.getLogo());
        logo.setFitWidth(35);
        logo.setFitHeight(35);
        Label yariLabel = new Label("Yari Editor");
        yariLabel.setStyle("-fx-text-fill: ghostwhite; -fx-font-size: 16px");
        branding.getChildren().addAll(logo, yariLabel);
        AnchorPane.setTopAnchor(branding, 0D);
        AnchorPane.setLeftAnchor(branding, 0D);
        AnchorPane.setBottomAnchor(branding, 0D);

        //menu
        Pane menu = new Pane();
        menu.setPrefSize(24, 16);
        menu.setMaxSize(24, 16);
        menu.setId("menu");
        StackPane menuWrapper = new StackPane(menu);
        AnchorPane.setLeftAnchor(menuWrapper, 185D);
        AnchorPane.setTopAnchor(menuWrapper, 0D);
        AnchorPane.setBottomAnchor(menuWrapper, 0D);
        Tooltip.install(menu, new Tooltip("Menu"));

        JFXPopup fileMenuPopUp = new JFXPopup();
        fileMenuPopUp.setAutoHide(true);
        JFXListView<HBox> fileMenuList = new JFXListView<>();
        //file menu
        PopupMenuEntry fileNewMenuEntry = new PopupMenuEntry("New", KeyboardShortcut.NEW);
        fileNewMenuEntry.setOnMouseClicked(me -> {
            if (FileUtil.isDirty()) {
                handleDirty();
            }
            FileUtil.newFile();
            fileMenuPopUp.hide();
        });
        Tooltip.install(fileNewMenuEntry, new Tooltip("Start a new document."));
        PopupMenuEntry fileOpenMenuEntry = new PopupMenuEntry("Open", KeyboardShortcut.OPEN);
        fileOpenMenuEntry.setOnMouseClicked(me -> {
            open();
            fileMenuPopUp.hide();
        });

        PopupMenuEntry importExcel = new PopupMenuEntry("Import From Excel", null);
        importExcel.setOnMouseClicked(me -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(getScene().getWindow());
            ExcelImporter excelImporter = new ExcelImporter();
            FXUtil.runAsync(() -> {
                loadingContent.setValue(true);
                controlsLock.setValue(true);
                DecisionTable decisionTable = null;
                try {
                    decisionTable = excelImporter.importFromExcel(file);
                } catch (IOException | InvalidFormatException e) {
                    ExcelImportResultsCard excelImportResultsCard = new ExcelImportResultsCard(e);
                    FXUtil.runOnFXThread(() -> {
                        displayedContent.getChildren().setAll(excelImportResultsCard);
                        loadingContent.setValue(false);
                        controlsLock.setValue(false);
                    });
                }
                decisionTableService.setDecisionTable(decisionTable);
                decisionTableService.updateFXListsFromTable();
                ExcelImportResultsCard excelImportResultsCard = new ExcelImportResultsCard(excelImporter.getErrorMessages());
                FXUtil.runOnFXThread(() -> {
                    displayedContent.getChildren().setAll(excelImportResultsCard);
                    loadingContent.setValue(false);
                    controlsLock.setValue(false);
                });
            });

        });
        Tooltip.install(importExcel, new Tooltip("Import from Excel."));

        Tooltip.install(fileOpenMenuEntry, new Tooltip("Open a previously saved document."));
        PopupMenuEntry fileSaveMenuEntry = new PopupMenuEntry("Save", KeyboardShortcut.SAVE);
        fileSaveMenuEntry.setOnMouseClicked(me -> {
            save(false);
            fileMenuPopUp.hide();
        });
        Tooltip.install(fileSaveMenuEntry, new Tooltip("Save the current document."));
        fileSaveMenuEntry.disableProperty().bind(FileUtil.fileProperty().isNull());
        PopupMenuEntry fileSaveAsMenuEntry = new PopupMenuEntry("Save As..");
        fileSaveAsMenuEntry.setOnMouseClicked(me -> {
            save(true);
            fileMenuPopUp.hide();
        });
        Tooltip.install(fileSaveAsMenuEntry, new Tooltip("Save the current document as a new file."));
        PopupMenuEntry fileSettingsMenuEntry = new PopupMenuEntry("Settings");
        fileSettingsMenuEntry.setOnMouseClicked(me -> {
            Stage stage = new Stage();
            Scene scene = new Scene(new SettingsView(SettingsUtil.getSettings(), stage));
            ThemeUtil.setThemeOnScene(scene);
            stage.setScene(scene);
            stage.show();

        });
        PopupMenuEntry filePrintMenuEntry = new PopupMenuEntry("Print", KeyboardShortcut.PRINT);
        filePrintMenuEntry.setOnMouseClicked(me -> {
            FileUtil.print(decisionTableService.getDecisionTable());
            fileMenuPopUp.hide();
        });
        Tooltip.install(filePrintMenuEntry, new Tooltip("Print the current document."));
        PopupMenuEntry fileExitMenuEntry = new PopupMenuEntry("Exit");
        fileExitMenuEntry.setOnMouseClicked(me -> {
            fileMenuPopUp.hide();
            Platform.exit();
        });
        Tooltip.install(fileExitMenuEntry, new Tooltip("Exit the application."));

        fileMenuList.getItems().addAll(fileNewMenuEntry, fileOpenMenuEntry, importExcel, fileSaveMenuEntry, fileSaveAsMenuEntry, fileSettingsMenuEntry, filePrintMenuEntry, fileExitMenuEntry);
        menu.setOnMouseClicked(me -> fileMenuPopUp.show(menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
        fileMenuPopUp.setPopupContent(fileMenuList);

        //window controls
        HBox controls = new HBox(20);
        controls.setPadding(new Insets(0, 15, 0, 0));
        AnchorPane.setRightAnchor(controls, 0D);
        AnchorPane.setTopAnchor(controls, 0D);
        AnchorPane.setBottomAnchor(controls, 0D);

        controls.setAlignment(Pos.CENTER_RIGHT);
        //close
        Pane closeButton = new Pane();
        closeButton.setId("windowClose");
        closeButton.setPrefSize(16, 16);
        closeButton.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        Tooltip.install(closeButton, new Tooltip("Exit"));
        closeButton.setOnMouseClicked(me -> Platform.exit());

        //minimize
        minimizeButton = new Pane();
        HBox.setMargin(minimizeButton, new Insets(7, 0, 0, 0));
        minimizeButton.setId("windowMinimize");
        minimizeButton.setPrefSize(16, 5);
        minimizeButton.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        Tooltip.install(minimizeButton, new Tooltip("Minimize"));

        //maximize
        maximizeButton = new Pane();
        maximizeButton.setId("windowMaximize");
        maximizeButton.setPrefSize(16, 16);
        maximizeButton.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        Tooltip.install(maximizeButton, new Tooltip("Maximize"));

        controls.getChildren().addAll(minimizeButton, maximizeButton, closeButton);

        header.getChildren().addAll(branding, menuWrapper, controls);

        //add it
        setTop(header);

    }


    private void selectTab(String tabTitle) {
        menuOptions.stream().filter(tab -> tabTitle.equalsIgnoreCase(tab.getTitle())).findFirst().ifPresent(tab -> {
            menuOptions.forEach(MenuOption::deselect);
            tab.select();
        });
    }


    private void prepareOptions() {
        MenuOption actionsTool = new MenuOption("Actions", "mdTab-actions");
        actionsTool.disableProperty().bind(controlsLock);
        Tooltip.install(actionsTool, new Tooltip("Open the Actions Editor"));
        actionsTool.setOnMouseClicked(me -> selectTab("actions"));
        actionsTool.setOnSelectAction(() -> showView(ToolView.ACTIONS));

        MenuOption conditionsTool = new MenuOption("Conditions", "mdTab-conditions");
        conditionsTool.disableProperty().bind(controlsLock);
        Tooltip.install(conditionsTool, new Tooltip("Open the Conditions Editor"));
        conditionsTool.setOnMouseClicked(me -> selectTab("conditions"));
        conditionsTool.setOnSelectAction(() -> showView(ToolView.CONDITIONS));

        MenuOption rowsTool = new MenuOption("Rows", "mdTab-rows");
        rowsTool.disableProperty().bind(controlsLock);
        Tooltip.install(rowsTool, new Tooltip("Open the Rows Editor"));
        rowsTool.setOnMouseClicked(me -> selectTab("rows"));
        rowsTool.setOnSelectAction(() -> showView(ToolView.ROWS));

        MenuOption generalTool = new MenuOption("General", "mdTab-general");
        generalTool.disableProperty().bind(controlsLock);
        Tooltip.install(generalTool, new Tooltip("Open the General Table Setting Editor"));
        generalTool.setOnMouseClicked(me -> selectTab("general"));
        generalTool.setOnSelectAction(() -> showView(ToolView.GENERAL));

        MenuOption javaCodeTool = new MenuOption("Java Code", "mdTab-code");
        javaCodeTool.disableProperty().bind(controlsLock);
        Tooltip.install(javaCodeTool, new Tooltip("View Skeleton Java Code"));
        javaCodeTool.setOnSelectAction(() -> showView(ToolView.JAVA_CODE));
        javaCodeTool.setOnMouseClicked(me -> selectTab("java code"));

        menuOptions.addAll(actionsTool, conditionsTool, rowsTool, generalTool, javaCodeTool);

        VBox optionsContainer = new VBox();
        optionsContainer.getChildren().setAll(actionsTool, conditionsTool, rowsTool, generalTool, javaCodeTool);

        optionsContainer.setPadding(new Insets(25, 0, 0, 0));
        AnchorPane.setTopAnchor(optionsContainer, 0D);
        AnchorPane.setLeftAnchor(optionsContainer, 0D);
        AnchorPane.setRightAnchor(optionsContainer, 0D);

        //container
        AnchorPane leftMenu = new AnchorPane();
        leftMenu.setId("leftMenu");
        leftMenu.getChildren().add(optionsContainer);

        JFXSpinner backgroundBusyIndicator = new JFXSpinner();
        backgroundBusyIndicator.setRadius(5);
        backgroundBusyIndicator.managedProperty().bind(validationService.busyProperty());
        backgroundBusyIndicator.visibleProperty().bind(validationService.busyProperty());
        AnchorPane.setRightAnchor(backgroundBusyIndicator, 5D);
        AnchorPane.setBottomAnchor(backgroundBusyIndicator, 5D);
        leftMenu.getChildren().add(backgroundBusyIndicator);

        Pane validIndicator = new Pane();
        BooleanBinding notBusyAndEnabled = Bindings.and(validationService.busyProperty().not(), validationService.enabledProperty());
        validIndicator.managedProperty().bind(notBusyAndEnabled);
        validIndicator.visibleProperty().bind(notBusyAndEnabled);
        Tooltip validTip = new Tooltip();
        Tooltip.install(validIndicator, validTip);
        AnchorPane.setRightAnchor(validIndicator, 5D);
        AnchorPane.setBottomAnchor(validIndicator, 5D);
        validIndicator.setPrefSize(16, 16);
        validIndicator.getStyleClass().add("validationIndicator");
        leftMenu.getChildren().add(validIndicator);

        PseudoClass success = PseudoClass.getPseudoClass("success");
        validIndicator.pseudoClassStateChanged(success, validationService.validProperty().get());//trigger at load
        final String validTipText = "Data passes validation. CTRL+V to re-validate.";
        final String invalidTipText = "Data fails validation. CTRL+V to re-validate. Click to see message.";
        validTip.setText(validationService.validProperty().get() ? validTipText : invalidTipText);
        validationService.validProperty().addListener((obs, ov, isValid) -> {
            validIndicator.pseudoClassStateChanged(success, isValid);
            validTip.setText(isValid ? validTipText : invalidTipText);
        });


        final ValidationLogDialog validationLogDialog = new ValidationLogDialog(displayedContent);
        loadingContent.addListener(c -> validationLogDialog.setVisible(false));
        validationService.validProperty().addListener((obs, ov, valid) -> {
            if (!valid) {
                validIndicator.setOnMouseClicked(me -> {
                    if (me.getButton() == MouseButton.PRIMARY && !validationLogDialog.isVisible()) {
                        validationLogDialog.refreshLog();
                        validationLogDialog.show();
                    }
                });
            } else {
                validIndicator.setOnMouseClicked(null);
            }
        });

        //style it
        leftMenu.setPrefSize(175, USE_COMPUTED_SIZE);

        setLeft(leftMenu);

    }


    public void handleDirty() {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label("You have unsaved changes. Do you want to save before continuing?"));

        JFXAlert<Void> alert = new JFXAlert<>((Stage) getScene().getWindow());

        JFXButton saveButton = new JFXButton("SAVE");
        saveButton.getStyleClass().add("button-flat-gray");
        saveButton.setOnMouseClicked(me -> {
            alert.hide();
            save(false);
        });
        JFXButton dismissButton = new JFXButton("DISMISS");
        dismissButton.getStyleClass().add("button-flat-gray");
        dismissButton.setOnMouseClicked(me -> {
            alert.hideWithAnimation();
        });
        layout.setActions(saveButton, dismissButton);
        alert.setOverlayClose(false);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    public void showView(ToolView toolView) {
        loadingContent.set(true);

        FXUtil.runAsync(() -> {
            Pane displayPane;
            switch (toolView) {
                case GENERAL:
                    displayPane = new GeneralToolView();
                    break;
                case ROWS:
                    displayPane = new RowsToolView();
                    break;
                case ACTIONS:
                    displayPane = new ActionsToolView();
                    break;
                case CONDITIONS:
                    displayPane = new ConditionsToolView();
                    break;
                case JAVA_CODE:
                    displayPane = new JavaCodeToolView();
                    break;
                default:
                    displayPane = new StackPane(new Label("An error occurred. Please contact the developer."));
                    break;
            }
            FXUtil.runOnFXThread(() -> {
                this.displayedContent.getChildren().setAll(displayPane);
                loadingContent.set(false);
            });
        });
    }

    public void open() {
        if (FileUtil.isDirty()) {
            handleDirty();
        }
        FileUtil.openDecisionTableFile((Stage) getScene().getWindow(), null);
    }


    public void save(boolean newFile) {
        if (!validationService.validProperty().get()) {
            ToastUtil.sendPersistentToast("Could not save decision table as it does not pass validation.");
        } else if (newFile) {
            //save as functionality
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show the save file dialog
            File file = fileChooser.showSaveDialog(getScene().getWindow());

            if (file != null) {
                // Make sure it has the correct extension.
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                FileUtil.saveDecisionTableToFile(file);
            }
        } else {
            File tableFile = FileUtil.getCurrentFile();
            if (tableFile != null) {
                FileUtil.saveDecisionTableToFile(FileUtil.getCurrentFile());
            } else {
                save(true); //recurse
            }
        }
    }

    /**
     * Returns the Rows view, if displayed.
     *
     * @return the rows view, if displayed.
     */
    public Optional<DataEditor> getDataEditor() {
        return displayedContent.getChildren().stream()
                .filter(DataEditor.class::isInstance)
                .map(DataEditor.class::cast)
                .findAny();
    }


    public Pane getMinimizeButton() {
        return minimizeButton;
    }

    public Pane getMaximizeButton() {
        return maximizeButton;
    }

    public AnchorPane getHeader() {
        return header;
    }


    public JFXSnackbar getToastBar() {
        return toastBar;
    }
}
