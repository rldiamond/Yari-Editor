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

import view.GeneralView;

/**
 * Validates the table information: table name, table description.
 */
public class TableInformationValidator extends TableValidator {

    private static final boolean NAME_REQUIRED = true;
    private static final boolean DESCRIPTION_REQUIRED = true;

    public TableInformationValidator(boolean strict) {
        super("Table Information Validator", strict);
    }

    @Override
    public void run() {

        final String tableName = getDecisionTable().getTableName();
        if (NAME_REQUIRED) {
            if (tableName == null) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(GeneralView.class);
                ValidatorError validatorError = new ValidatorError("A table name is required and not provided.", validatorErrorLocation);
                addError(validatorError);
            }

            if ("".equals(tableName)) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(GeneralView.class);
                ValidatorError validatorError = new ValidatorError("A table name is required and not provided.", validatorErrorLocation);
                addError(validatorError);
            }
        }

        final String tableDescription = getDecisionTable().getTableDescription();
        if (DESCRIPTION_REQUIRED) {
            if (tableDescription == null) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(GeneralView.class);
                ValidatorError validatorError = new ValidatorError("A table description is required and not provided.", validatorErrorLocation);
                addError(validatorError);
            }

            if ("".equals(tableDescription)) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(GeneralView.class);
                ValidatorError validatorError = new ValidatorError("A table description is required and not provided.", validatorErrorLocation);
                addError(validatorError);
            }
        }

    }
}
