package utilities;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.yari.core.BasicRule;
import org.yari.core.Context;
import org.yari.core.YariException;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.Row;
import view.RootLayoutFactory;

import java.io.File;

public class FileUtil {

    private static File currentFile;

    public static boolean openFile(Window window) {
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            return importFromFile(file);
        }
        return false;
    }

    private static boolean importFromFile(File file) {
        clearData();

        if (!DecisionTableValidator.isValidXML(file.getPath())) {
            return false;
        }

        DecisionTable decisionTable;

        //get around some weird yari stuff to import the table
        BasicRule basicRule = new BasicRule() {
            @Override
            public void lookupGlobals(Context globalContext) {
            }
        };

        currentFile = file;

        try {
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
        } catch (YariException ex) {
            //TODO: Didnt validate.. show an error
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            //TODO: other loading issue.. show a diff error
            ex.printStackTrace();
            return false;
        }

        RootLayoutFactory.getInstance().setDecisionTable(decisionTable);
        return true;
    }

    private static void clearData() {
        RootLayoutFactory.getInstance().getRowsList().clear();
        RootLayoutFactory.getInstance().getActionsList().clear();
        RootLayoutFactory.getInstance().getConditionsList().clear();
        RootLayoutFactory.getInstance().setDecisionTable(null);
    }

}
