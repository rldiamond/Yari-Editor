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
import components.DialogPane;
import components.PopupMenuEntry;
import components.UpdateEvent;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import utilities.*;

import java.io.File;

public class RootLayout extends BorderPane {

    private DecisionTable decisionTable;
    private final ObservableList<Action> actionsList = FXCollections.observableArrayList();
    private final ObservableList<Condition> conditionsList = FXCollections.observableArrayList();
    private final ObservableList<Row> rowsList = FXCollections.observableArrayList();
    private final StringProperty ruleName = new SimpleStringProperty("MyTableRule");
    private final ObservableList<MenuOption> menuOptions = FXCollections.observableArrayList();
    private final BooleanProperty loadingContent = new SimpleBooleanProperty(false);
    private final StackPane displayedContent = new StackPane();
    private final AnchorPane header = new AnchorPane();
    private final DecisionTableValidator decisionTableValidator = DecisionTableValidator.getInstance();
    private Pane minimizeButton;
    private JFXSnackbar toastBar;

    public RootLayout() {

        //when loadingContent, display a loading indicator
        var loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(30);
        loadingSpinner.visibleProperty().bind(loadingContent);
        loadingSpinner.managedProperty().bind(loadingContent);
        //blank pane to cover up any displayed content
        var loadingPane = new StackPane();
        loadingPane.setStyle("-fx-background-color: white");
        var fadePaneOut = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.OUT);
        var fadePaneIn = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.IN);

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
        var mainPanel = new StackPane();
        mainPanel.getChildren().setAll(displayedContent, loadingPane, loadingSpinner);

        toastBar = new JFXSnackbar(mainPanel);
        toastBar.setPrefWidth(350);
        DecisionTableValidator.getInstance().validProperty().addListener((obs, ov, isValid) -> {
            if (!isValid) {
                toastBar.enqueue(new JFXSnackbar.SnackbarEvent("Decision table no longer validates! " + decisionTableValidator.getMessage(),
                        "DISMISS",
                        3000,
                        true,
                        b -> toastBar.close()));
            }
        });

        //initial (minimal loading required)
        prepareOptions();
        prepareHeader();
        setCenter(mainPanel);

        //validate table anytime changes are made to the lists
        actionsList.addListener((ListChangeListener.Change<? extends Action> c) -> decisionTableValidator.requestValidation());
        conditionsList.addListener((ListChangeListener.Change<? extends Condition> c) -> decisionTableValidator.requestValidation());
        rowsList.addListener((ListChangeListener.Change<? extends Row> c) -> decisionTableValidator.requestValidation());
        addEventHandler(UpdateEvent.UPDATE, e -> decisionTableValidator.requestValidation());

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

        JFXPopup fileMenuPopUp = new JFXPopup();
        fileMenuPopUp.setAutoHide(true);
        JFXListView<HBox> fileMenuList = new JFXListView<>();
        //file menu
        PopupMenuEntry file_new_pane = new PopupMenuEntry("New", KeyboardShortcut.NEW);
        file_new_pane.setOnMouseClicked(me -> {
            if (FileUtil.isDirty()){
                handleDirty();
            }
            FileUtil.clearData();
            DecisionTable decisionTable = new DecisionTable();
            decisionTable.setName("MyTable");
            decisionTable.setDescription("MyTable Description");
            setDecisionTable(decisionTable);
            fileMenuPopUp.hide();
        });
        PopupMenuEntry file_open_pane = new PopupMenuEntry("Open", KeyboardShortcut.OPEN);
        file_open_pane.setOnMouseClicked(me -> {
            open();
            fileMenuPopUp.hide();
        });
        PopupMenuEntry file_save_pane = new PopupMenuEntry("Save", KeyboardShortcut.SAVE);
        file_save_pane.setOnMouseClicked(me -> {
            save(false);
            fileMenuPopUp.hide();
        });
        PopupMenuEntry file_saveAs_pane = new PopupMenuEntry("Save As..", null);
        file_saveAs_pane.setOnMouseClicked(me -> {
            save(true);
            fileMenuPopUp.hide();
        });
        PopupMenuEntry file_print_pane = new PopupMenuEntry("Print", KeyboardShortcut.PRINT);
        file_print_pane.setOnMouseClicked(me -> {
            FileUtil.print();
            fileMenuPopUp.hide();
        });
        PopupMenuEntry file_exit_pane = new PopupMenuEntry("Exit", null);
        file_exit_pane.setOnMouseClicked(me -> {
            fileMenuPopUp.hide();
            Platform.exit();
        });

        fileMenuList.getItems().addAll(file_new_pane, file_open_pane, file_save_pane, file_saveAs_pane, file_print_pane, file_exit_pane);
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

        controls.getChildren().addAll(minimizeButton, closeButton);

        header.getChildren().addAll(branding, menuWrapper, controls);

        //add it
        setTop(header);

    }

    private void selectTab(String tabTitle) {
        menuOptions.stream()
                .filter(tab -> tabTitle.equalsIgnoreCase(tab.getTitle()))
                .findFirst()
                .ifPresent(tab -> {
                    menuOptions.forEach(MenuOption::deselect);
                    tab.select();
                });
    }

    private void prepareOptions() {
        //TODO: on select of any option, load the appropriate view
        var actionsOption = new MenuOption("Actions", "mdTab-actions");
        Tooltip.install(actionsOption, new Tooltip("Open the Actions Editor"));
        actionsOption.setOnMouseClicked(me -> selectTab("actions"));
        actionsOption.setOnSelectAction(() -> {
            loadingContent.set(true);

            FXUtil.runAsync(() -> {
                ActionsView actionsView = new ActionsView();

                FXUtil.runOnFXThread(() -> {
                    displayedContent.getChildren().setAll(actionsView);
                    loadingContent.set(false);
                });
            });
        });
        var conditionsOption = new MenuOption("Conditions", "mdTab-conditions");
        Tooltip.install(conditionsOption, new Tooltip("Open the Conditions Editor"));
        conditionsOption.setOnMouseClicked(me -> selectTab("conditions"));
        conditionsOption.setOnSelectAction(() -> {
            loadingContent.set(true);

            FXUtil.runAsync(() -> {
                ConditionsView conditionsView = new ConditionsView();

                FXUtil.runOnFXThread(() -> {
                    displayedContent.getChildren().setAll(conditionsView);
                    loadingContent.set(false);
                });
            });
        });
        var rowsOption = new MenuOption("Rows", "mdTab-rows");
        Tooltip.install(rowsOption, new Tooltip("Open the Rows Editor"));
        rowsOption.setOnMouseClicked(me -> selectTab("rows"));
        rowsOption.setOnSelectAction(() -> {
            loadingContent.set(true);

            FXUtil.runAsync(() -> {
                RowsView rowsView = new RowsView();

                FXUtil.runOnFXThread(() -> {
                    displayedContent.getChildren().setAll(rowsView);
                    loadingContent.set(false);
                });
            });
        });
        var generalOption = new MenuOption("General", "mdTab-general");
        Tooltip.install(generalOption, new Tooltip("Open the General Table Setting Editor"));
        generalOption.setOnMouseClicked(me -> selectTab("general"));
        generalOption.setOnSelectAction(() -> {
            loadingContent.set(true);

            FXUtil.runAsync(() -> {

                GeneralView generalView = new GeneralView();

                FXUtil.runOnFXThread(() -> {
                    this.displayedContent.getChildren().setAll(generalView);
                    loadingContent.set(false);
                });

            });


        });
        var skeletonCode = new MenuOption("Java Code", "mdTab-code");
        Tooltip.install(skeletonCode, new Tooltip("View Skeleton Java Code"));
        skeletonCode.setOnSelectAction(() -> {
            loadingContent.set(true);

            FXUtil.runAsync(() -> {
                JavaCodeView javaCodeView = new JavaCodeView();

                FXUtil.runOnFXThread(() -> {
                    this.displayedContent.getChildren().setAll(javaCodeView);
                    loadingContent.set(false);
                });
            });


        });
        skeletonCode.setOnMouseClicked(me -> selectTab("java code"));

        menuOptions.addAll(actionsOption, conditionsOption, rowsOption, generalOption, skeletonCode);

        var optionsContainer = new VBox();
        optionsContainer.getChildren().setAll(actionsOption, conditionsOption, rowsOption, generalOption, skeletonCode);

        optionsContainer.setPadding(new Insets(25, 0, 0, 0));
        AnchorPane.setTopAnchor(optionsContainer, 0D);
        AnchorPane.setLeftAnchor(optionsContainer, 0D);
        AnchorPane.setRightAnchor(optionsContainer, 0D);

        //container
        var leftMenu = new AnchorPane();
        leftMenu.setId("leftMenu");
        leftMenu.getChildren().add(optionsContainer);

        JFXSpinner backgroundBusyIndicator = new JFXSpinner();
        backgroundBusyIndicator.setRadius(5);
        backgroundBusyIndicator.managedProperty().bind(decisionTableValidator.busyProperty());
        backgroundBusyIndicator.visibleProperty().bind(decisionTableValidator.busyProperty());
        AnchorPane.setRightAnchor(backgroundBusyIndicator, 5D);
        AnchorPane.setBottomAnchor(backgroundBusyIndicator, 5D);
        leftMenu.getChildren().add(backgroundBusyIndicator);

        //valid indicator
        Pane validIndicator = new Pane();
        JFXDialog messageDialog = new JFXDialog();

        DialogPane dialogPane = new DialogPane(messageDialog);
        dialogPane.contentProperty().bind(decisionTableValidator.messageProperty());
        messageDialog.setContent(dialogPane);
        messageDialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
        messageDialog.setDialogContainer(displayedContent);


        validIndicator.managedProperty().bind((decisionTableValidator.busyProperty().not()));
        validIndicator.visibleProperty().bind((decisionTableValidator.busyProperty().not()));
        Tooltip validTip = new Tooltip();
        Tooltip.install(validIndicator, validTip);
        AnchorPane.setRightAnchor(validIndicator, 5D);
        AnchorPane.setBottomAnchor(validIndicator, 5D);
        validIndicator.setPrefSize(16, 16);
        validIndicator.getStyleClass().add("validationIndicator");
        leftMenu.getChildren().add(validIndicator);

        PseudoClass success = PseudoClass.getPseudoClass("success");
        validIndicator.pseudoClassStateChanged(success, decisionTableValidator.validProperty().get());//trigger at load
        final String validTipText = "Data passes validation!";
        final String invalidTipText = "Data fails validation! Click to see message.";
        validTip.setText(decisionTableValidator.validProperty().get() ? validTipText : invalidTipText);
        decisionTableValidator.validProperty().addListener((obs, ov, isValid) -> {
            validIndicator.pseudoClassStateChanged(success, isValid);
            validTip.setText(isValid ? validTipText : invalidTipText);
        });

        decisionTableValidator.validProperty().addListener((obs, ov, valid) -> {
            if (!valid) {
                validIndicator.setOnMouseClicked(me -> {
                    if (me.getButton() == MouseButton.PRIMARY) {
                        messageDialog.show();
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

    public void handleDirty(){
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

    public void open(){
        if (FileUtil.isDirty()){
            handleDirty();
        }
        FileUtil.openFile((Stage) getScene().getWindow());
    }

    public void save(boolean newFile) {
        if (!decisionTableValidator.validProperty().get()) {
            ToastUtil.sendPersistantToast("Could not save decision table as it does not pass validation.");
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
                FileUtil.saveToFile(file);
            }
        } else {
            File tableFile = FileUtil.getCurrentFile();
            if (tableFile != null) {
                FileUtil.saveToFile(FileUtil.getCurrentFile());
            } else {
                save(true); //recurse
            }
        }
    }

    public Pane getMinimizeButton() {
        return minimizeButton;
    }

    public AnchorPane getHeader() {
        return header;
    }

    public ObservableList<Action> getActionsList() {
        return actionsList;
    }

    public ObservableList<Condition> getConditionsList() {
        return conditionsList;
    }

    public ObservableList<Row> getRowsList() {
        return rowsList;
    }

    public DecisionTable getDecisionTable() {
        return decisionTable;
    }

    public String getRuleName() {
        return ruleName.get();
    }

    public StringProperty ruleNameProperty() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName.set(ruleName);
    }

    public void setDecisionTable(DecisionTable decisionTable) {
        this.decisionTable = decisionTable;
    }

    public JFXSnackbar getToastBar() {
        return toastBar;
    }
}
