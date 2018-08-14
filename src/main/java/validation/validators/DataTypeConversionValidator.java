/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package validation.validators;

import objects.DataType;
import objects.ToolView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.TableRow;

import java.util.List;

/**
 * Validate the data in each cell can be converted to the specified data type.
 * <p>
 * Strict: Will not allow nulls
 */
public class DataTypeConversionValidator extends TableValidator {

    private static Logger logger = LoggerFactory.getLogger(DataTypeConversionValidator.class);

    public DataTypeConversionValidator(boolean strict) {
        super("Data type conversion validator", strict);
    }

    @Override
    protected void runValidation() {
        List<TableRow> rows = getDecisionTable().getRawRowData();
        List<TableCondition> conditions = getDecisionTable().getTableConditions();
        List<TableAction> actions = getDecisionTable().getTableActions();

        if (rows == null || conditions == null || actions == null) {
            //Note: this is taken care of in MinimumRequiredDataValidator
            return;
        }

        //results = actions, values = conditions

        //check conditions
        conditions.forEach(condition -> {

            final int conditionIndex = conditions.indexOf(condition);
            final DataType dataType = DataType.getFromTableString(condition.getDataType());
            if (dataType == null) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setToolView(ToolView.CONDITIONS);
                validatorErrorLocation.setTableCondition(condition);
                validatorErrorLocation.setTableColumnNumber(1 + conditionIndex);
                String errMsg = "The condition '" + condition.getName() + "' has an invalid data type of '" + condition.getDataType() + "'";
                ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                addError(validatorError);
            }

            rows.forEach(row -> {

                // this is likely a new row, and does not contain a value for the condition
                if (row.getValues().size() <= conditionIndex) {
                    ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                    validatorErrorLocation.setTableRowNumber(row.getRowNumber());
                    validatorErrorLocation.setTableRow(row);
                    validatorErrorLocation.setToolView(ToolView.ROWS);
                    validatorErrorLocation.setTableColumnNumber(1 + conditionIndex);
                    String errMsg = "Row " + row.getRowNumber() + " does not have a value for condition column " + condition.getName();
                    ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                    addError(validatorError);
                } else {

                    String data = row.getValues().get(conditionIndex);
                    if (!isStrict() && data == null) {
                        //.. we don't validate nulls non-strict
                    } else if (!canConvert(data, dataType)) {
                        ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                        validatorErrorLocation.setTableCondition(condition);
                        validatorErrorLocation.setTableColumnNumber(1 + conditionIndex);
                        validatorErrorLocation.setToolView(ToolView.ROWS);
                        validatorErrorLocation.setTableRow(row);
                        validatorErrorLocation.setTableRowNumber(row.getRowNumber());
                        String errMsg = "The value '" + data + "' in row " + row.getRowNumber() + ", column " + condition.getName() + " cannot be converted to " +
                                "the specified data type";
                        ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                        addError(validatorError);
                    }
                }

            });

        });

        //check actions
        actions.forEach(action -> {

            final int actionIndex = actions.indexOf(action);
            final DataType dataType = DataType.getFromTableString(action.getDataType());
            if (dataType == null) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setToolView(ToolView.ACTIONS);
                validatorErrorLocation.setTableAction(action);
                validatorErrorLocation.setTableColumnNumber(1 + conditions.size() + actionIndex);
                String errMsg = "The action '" + action.getName() + "' has an invalid data type of '" + action.getDataType() + "'";
                ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                addError(validatorError);
            }

            rows.forEach(row -> {

                // this is likely a new row, and does not contain a value for the condition
                if (row.getResults().size() <= actionIndex) {
                    ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                    validatorErrorLocation.setTableRowNumber(row.getRowNumber());
                    validatorErrorLocation.setTableRow(row);
                    validatorErrorLocation.setToolView(ToolView.ROWS);
                    validatorErrorLocation.setTableColumnNumber(1 + conditions.size() + actionIndex);
                    String errMsg = "Row " + row.getRowNumber() + " does not have a value for action column " + action.getName();
                    ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                    addError(validatorError);
                } else {

                    String data = row.getResults().get(actionIndex);
                    if (!isStrict() && data == null) {
                        //.. we don't validate nulls non-strict
                    } else if (!canConvert(data, dataType)) {
                        ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                        validatorErrorLocation.setTableAction(action);
                        validatorErrorLocation.setTableColumnNumber(1 + conditions.size() + actionIndex);
                        validatorErrorLocation.setToolView(ToolView.ROWS);
                        validatorErrorLocation.setTableRow(row);
                        validatorErrorLocation.setTableRowNumber(row.getRowNumber());
                        String errMsg = "The value '" + data + "' in row " + row.getRowNumber() + ", column " + action.getName() + " cannot be converted to " +
                                "the specified data type";
                        ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                        addError(validatorError);
                    }
                }

            });

        });

    }

    private boolean canConvert(String data, DataType dataType) {
        boolean canConvert = true;

        if (data == null) {
            return false;
        }

        data = data.trim();

        try {
            switch (dataType) {
                case BOOLEAN:
                    Boolean.parseBoolean(data);
                    if (!(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false"))) {
                        //note: Boolean.parseBoolean does not throw exception if fail, only returns false.
                        throw new Exception("Invalid boolean data");
                    }
                    break;
                case DOUBLE:
                    Double.parseDouble(data);
                    break;
                case CHAR:
                    if (data.length() > 1) {
                        canConvert = false;
                    }
                    data.charAt(0);
                    break;
                case BYTE:
                    Byte.parseByte(data);
                    break;
                case FLOAT:
                    Float.parseFloat(data);
                    break;
                case INTEGER:
                    Integer.parseInt(data);
                    break;
                case LONG:
                    Long.parseLong(data);
                    break;
                case SHORT:
                    Short.parseShort(data);
                    break;
                case STRING:
                    //NO VALIDATION REQUIRED
                    break;
                default:
                    logger.error("Unsupported data type provided!");
                    break;
            }
        } catch (Exception ex) {
            canConvert = false;            
        }

        return canConvert;
    }
}
