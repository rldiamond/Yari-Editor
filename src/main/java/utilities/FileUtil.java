package utilities;

import com.thoughtworks.xstream.XStream;
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
import view.RootLayoutFactory;
import view.TablePrintView;
import view.WelcomeSplashFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//TODO: Print. Shortcuts to open, save, print. On save show a toast notification.
public class FileUtil {

    private static File currentFile;

    public static void openFile(Stage stage) {
        DecisionTableValidator.getInstance().setEnabled(false);
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(stage); //must be on fx thread
        if (file == null) {
            WelcomeSplashFactory.getInstance().busyProperty().set(false);
            return;
        }

        FXUtil.runAsync(() -> {
            try {
                importFromFile(file); //want off thread
                if (!RootLayoutFactory.isDisplayed()) {
                    FXUtil.runOnFXThread(() -> {
                        RootLayoutFactory.show(stage);
                        DecisionTableValidator.getInstance().setEnabled(true);
                    });

                }
            } catch (Exception ex) {
                WelcomeSplashFactory.getInstance().busyProperty().set(false);
                FXUtil.runOnFXThread(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not load table data");
                    alert.setContentText("Could not load table data from file.\n" + ex.getMessage());
                    alert.showAndWait();
                });
                DecisionTableValidator.getInstance().setEnabled(true);
            }
        });

    }

    public static void saveToFile(File file) {
        FXUtil.runAsync(() -> {
            DecisionTableValidator.getInstance().updateTable();
            DecisionTableValidator.getInstance().runValidation();

            if (!DecisionTableValidator.getInstance().validProperty().get()) {
                return;
            }

            XStream xstream = new XStream();
            xstream.processAnnotations(DecisionTable.class);

            try (FileOutputStream out = new FileOutputStream(file)) {
                xstream.toXML(RootLayoutFactory.getInstance().getDecisionTable(), out);
                currentFile = file;
                ToastUtil.sendToast("File saved.");
            } catch (Exception ex) {
                ToastUtil.sendPersistantToast("Failed to save file! " + ex.getMessage());
            }
        });
    }

    private static boolean importFromFile(File file) throws FileNotFoundException, YariException {
        clearData();

        DecisionTableValidator.getInstance().validateXML(file.getPath());

        DecisionTable decisionTable;

        //get around some weird yari stuff to import the table
        BasicRule basicRule = new BasicRule() {
            @Override
            public void lookupGlobals(Context globalContext) {
            }
        };

        currentFile = file;

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
        return true;
    }

    public static void print() {
        TablePrintView tablePrintView = new TablePrintView();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        double scaleX = pageLayout.getPrintableWidth() / tablePrintView.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / tablePrintView.getBoundsInParent().getHeight();
        tablePrintView.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(RootLayoutFactory.getInstance().getScene().getWindow())) {
            boolean success = job.printPage(tablePrintView);
            if (success) {
                job.endJob();
            }
        }
    }

    public static void clearData() {
        RootLayoutFactory.getInstance().getRowsList().clear();
        RootLayoutFactory.getInstance().getActionsList().clear();
        RootLayoutFactory.getInstance().getConditionsList().clear();
        RootLayoutFactory.getInstance().setDecisionTable(null);
    }

    public static File getCurrentFile() {
        return currentFile;
    }
}
