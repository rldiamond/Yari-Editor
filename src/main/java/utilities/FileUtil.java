/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import com.thoughtworks.xstream.XStream;
import javafx.beans.property.*;
import javafx.print.*;
import javafx.scene.control.Alert;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yari.core.BasicRule;
import org.yari.core.Context;
import org.yari.core.YariException;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import validation.ValidationService;
import validation.ValidationType;
import view.RootLayoutFactory;
import view.TablePrintView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Contains several file-management utilities such as save, open, and new files.
 */
public class FileUtil {

    private static ObjectProperty<File> currentFile = new SimpleObjectProperty<>(null);
    private static BooleanProperty dirty = new SimpleBooleanProperty(false);
    private static ValidationService validationService = ValidationService.getService();

    /**
     * Show a file chooser and attempt to open the selected file.
     *
     * @param stage the stage to base the file chooser on.
     */
    public static boolean openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(stage); //must be on fx thread
        if (file == null) {
            return false;
        }
        openFile(file, stage);
        return true;
    }

    public static void openFile(File file, Stage stage) {
        FXUtil.runAsync(() -> {
            if (!SettingsUtil.getSettings().getValidationType().equals(ValidationType.DISABLED)) {
                ValidationService.getService().setEnabled(false);
            }
            try {
                importFromFile(file); //want off thread
                if (!RootLayoutFactory.isDisplayed()) {
                    FXUtil.runOnFXThread(() -> {
                        RootLayoutFactory.show(stage);
                        if (!SettingsUtil.getSettings().getValidationType().equals(ValidationType.DISABLED)) {
                            validationService.setEnabled(true);
                        }
                    });

                }
            } catch (Exception ex) {
                FXUtil.runOnFXThread(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ValidatorError");
                    alert.setHeaderText("Could not load table data");
                    alert.setContentText("Could not load table data from file.\n" + ex.getMessage());
                    alert.showAndWait();
                });
                if (!SettingsUtil.getSettings().getValidationType().equals(ValidationType.DISABLED)) {
                    validationService.setEnabled(true);
                }
            }
        });
    }

    /**
     * Create a new file.
     */
    public static void newFile() {
        currentFile.setValue(null);
        clearData();
        DecisionTable decisionTable = new DecisionTable();
        decisionTable.setDescription("MyTable Description");
        decisionTable.setName("MyTable");

    }

    /**
     * Save the file.
     *
     * @param file the file to save.
     */
    public static void saveToFile(File file) {
        FXUtil.runAsync(() -> {
            TableUtil.updateTable();
            validationService.runValidationImmediately();

            if (!validationService.validProperty().get()) {
                return;
            }

            XStream xstream = new XStream();
            xstream.processAnnotations(DecisionTable.class);

            try (FileOutputStream out = new FileOutputStream(file)) {
                xstream.toXML(RootLayoutFactory.getInstance().getDecisionTable(), out);
                currentFile.setValue(file);
                SettingsUtil.addRecentFile(file);
                setDirty(false);
                ToastUtil.sendToast("File saved.");
            } catch (Exception ex) {
                ToastUtil.sendPersistentToast("Failed to save file! " + ex.getMessage());
            }
        });
    }

    private static boolean importFromFile(File file) throws FileNotFoundException, YariException {
        clearData();

        validationService.validateXML(file.getPath());

        DecisionTable decisionTable;

        //get around some weird yari stuff to import the table
        BasicRule basicRule = new BasicRule() {
            @Override
            public void lookupGlobals(Context globalContext) {
            }
        };

        currentFile.setValue(file);

        decisionTable = basicRule.createDecisionTable(file.getPath());
        for (Condition condition : decisionTable.getConditions()) {
            RootLayoutFactory.getInstance().getConditionsList().add(condition);
        }
        for (Action action : decisionTable.getActions()) {
            RootLayoutFactory.getInstance().getActionsList().add(action);
        }
        var rowNumber = 0;
        for (Row row : decisionTable.getRawRowData()) {
            row.setRowNumber(rowNumber++);
            RootLayoutFactory.getInstance().getRowsList().add(row);
        }


        RootLayoutFactory.getInstance().setDecisionTable(decisionTable);
        setDirty(false);
        return true;
    }

    /**
     * Print the table.
     */
    public static void print() {
        TablePrintView tablePrintView = new TablePrintView();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        double scaleX = pageLayout.getPrintableWidth() / 1920;
        double scaleY = pageLayout.getPrintableHeight() / 1080;
        tablePrintView.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        job.getJobSettings().setPageLayout(pageLayout);
        if (job != null && job.showPrintDialog(RootLayoutFactory.getInstance().getScene().getWindow())) {
            boolean success = job.printPage(tablePrintView);
            if (success) {
                job.endJob();
            }
        }
    }

    private static void clearData() {
        RootLayoutFactory.getInstance().getRowsList().clear();
        RootLayoutFactory.getInstance().getActionsList().clear();
        RootLayoutFactory.getInstance().getConditionsList().clear();
        RootLayoutFactory.getInstance().setDecisionTable(null);
    }

    public static File getCurrentFile() {
        return currentFile.get();
    }

    public static boolean isDirty() {
        return dirty.get();
    }

    public static BooleanProperty dirtyProperty() {
        return dirty;
    }

    public static void setDirty(boolean dirty) {
        FileUtil.dirty.set(dirty);
    }

    public static ReadOnlyObjectProperty<File> fileProperty() {
        return currentFile;
    }
}
