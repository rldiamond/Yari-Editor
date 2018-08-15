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
import objects.ComparatorType;
import objects.DataType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yari.core.table.DecisionTable;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.TableRow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelImporter.class);

    public DecisionTable importFromExcel(File file) throws IOException, InvalidFormatException {
        //create Apache POI workbook from .xls/.xlsx file
        Workbook workbook = WorkbookFactory.create(file);

        //NOTE: expect only one sheet, we only process first
        Sheet sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            LOGGER.warn("An error occurred when importing the Excel file!");
            return null;
        }

        DecisionTable decisionTable = new DecisionTable();
        List<ExcelDecisionTableItem> excelDecisionTableItemList = new ArrayList<>();

        sheet.forEach(row -> {
            int rowNum = row.getRowNum();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (rowNum) {
                case 0:
                    processTypeRow(row, excelDecisionTableItemList);
                    break;
                case 1:
                    processDataTypeRow(row, excelDecisionTableItemList);
                    break;
                case 2:
                    processComparatorTypeRow(row, excelDecisionTableItemList);
                    break;
                case 3:
                    processMethodNameRow(row, excelDecisionTableItemList);
                    break;
                case 4:
                    processNameRow(row, excelDecisionTableItemList);
                    populdateTable(excelDecisionTableItemList, decisionTable);
                    break;
                default:
                    processDataRow(row, excelDecisionTableItemList, decisionTable);
                    break;
            }

        });

        return decisionTable;
    }

    private void processDataRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList, DecisionTable decisionTable) {
        DataFormatter dataFormatter = new DataFormatter();
        TableRow tableRow = new TableRow();

        row.forEach(cell -> {

            if (cell.getColumnIndex() == 0) {
                return;//this is empty
            }

            String cellValue = dataFormatter.formatCellValue(cell).trim();

            int i = cell.getColumnIndex() - 1;
            ExcelDecisionTableItem excelDecisionTableItem = excelDecisionTableItemList.get(i);
            switch (excelDecisionTableItem.getType()) {
                case CONDITION:
                    tableRow.getValues().add(cellValue);
                    break;
                case ACTION:
                    tableRow.getResults().add(cellValue);
                    break;
            }

        });
        decisionTable.getRawRowData().add(tableRow);

    }

    private void populdateTable(List<ExcelDecisionTableItem> excelDecisionTableItemList, DecisionTable decisionTable) {
        excelDecisionTableItemList.forEach(item -> {

            switch (item.getType()) {
                case CONDITION:
                    TableCondition tableCondition = new TableCondition();
                    tableCondition.setComparator(item.getComparatorType().getDisplayValue());
                    tableCondition.setDataType(item.getDataType().getDisplayValue());
                    tableCondition.setMethodName(item.getMethodName());
                    tableCondition.setName(item.getName());
                    decisionTable.getTableConditions().add(tableCondition);
                    break;
                case ACTION:
                    TableAction tableAction = new TableAction();
                    tableAction.setDatatype(item.getDataType().getDisplayValue());
                    tableAction.setMethodname(item.getMethodName());
                    tableAction.setName(item.getName());
                    decisionTable.getTableActions().add(tableAction);
                    break;
            }

        });
    }

    private void processMethodNameRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {

            String cellValue = dataFormatter.formatCellValue(cell).trim();

            if ("METHOD NAME".equalsIgnoreCase(cellValue)) {
                return;
            }

            int i = cell.getColumnIndex() - 1;

            excelDecisionTableItemList.get(i).setMethodName(cellValue);

        });
    }

    private void processNameRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {

            String cellValue = dataFormatter.formatCellValue(cell).trim();

            if ("NAME".equalsIgnoreCase(cellValue)) {
                return;
            }

            int i = cell.getColumnIndex() - 1;

            excelDecisionTableItemList.get(i).setName(cellValue);

        });
    }

    private void processTypeRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {

            String cellValue = dataFormatter.formatCellValue(cell).trim().toUpperCase();

            ExcelDecisionTableItem excelTableItem = new ExcelDecisionTableItem();
            switch (cellValue) {
                case "TYPE":
                    break;
                case "CONDITION":
                    excelTableItem.setType(ExcelDecisionTableItem.Type.CONDITION);
                    excelDecisionTableItemList.add(excelTableItem);
                    break;
                case "ACTION":
                    excelTableItem.setType(ExcelDecisionTableItem.Type.ACTION);
                    excelDecisionTableItemList.add(excelTableItem);
                    break;
                default:
                    break;
            }

        });
    }

    private void processDataTypeRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {

            String cellValue = dataFormatter.formatCellValue(cell).trim().toUpperCase();

            if ("DATA TYPE".equalsIgnoreCase(cellValue)) {
                return;
            }

            DataType dataType = DataType.getFromTableString(cellValue);
            if (dataType == null) {
                return;
            }

            int i = cell.getColumnIndex() - 1;

            excelDecisionTableItemList.get(i).setDataType(dataType);

        });
    }

    private void processComparatorTypeRow(Row row, List<ExcelDecisionTableItem> excelDecisionTableItemList) {
        DataFormatter dataFormatter = new DataFormatter();

        row.forEach(cell -> {

            String cellValue = dataFormatter.formatCellValue(cell).trim().toUpperCase();

            if ("COMPARATOR TYPE".equalsIgnoreCase(cellValue)) {
                return;
            }

            ComparatorType comparatorType = ComparatorType.getFromTableString(cellValue);
            if (comparatorType == null) {
                return;
            }

            int i = cell.getColumnIndex() - 1;

            excelDecisionTableItemList.get(i).setComparatorType(comparatorType);

        });
    }

    public void exportToExcel(DecisionTable decisionTable) {

    }

}
