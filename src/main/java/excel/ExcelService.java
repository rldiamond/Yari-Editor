/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package excel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.yari.core.table.DecisionTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    private final DoubleProperty progress = new SimpleDoubleProperty(0);

    public DecisionTable importFromExcel(File file) throws IOException, InvalidFormatException {
        setProgress(5);
        //create Apache POI workbook from .xls/.xlsx file
        Workbook workbook = WorkbookFactory.create(file);
        setProgress(20);

        //NOTE: expect only one sheet, we only process first
        Sheet sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            return null;
        }

        final double progressStepSize = getProgressStepSize(sheet, 20, 80);


        DecisionTable decisionTable = new DecisionTable();
        List<ExcelDecisionTable> excelDecisionTableList = new ArrayList<>();

        sheet.forEach(row -> {
            if (row.getRowNum() <= 5) {
                // header row
            }

        });

        //TODO: Loop through and create table (20-80)

        //TODO: verification
        setProgress(100);

        return null;
    }

    private void processHeaderRow(Row row, List<ExcelDecisionTable> excelDecisionTableList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {
            String cellValue = dataFormatter.formatCellValue(cell).trim();
        });
    }

    private double getProgressStepSize(Sheet sheet, double start, double end) {
        double diff = end - start;

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        int iterations = lastRowNum - firstRowNum;

        return diff / iterations;
    }

    public void exportToExcel(DecisionTable decisionTable) {

    }

    private void setProgress(double d) {
        progress.setValue(d);
    }

    public DoubleProperty progressProperty() {
        return progress;
    }
}
