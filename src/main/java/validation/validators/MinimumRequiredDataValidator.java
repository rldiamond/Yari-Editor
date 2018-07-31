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

import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.Row;
import view.editors.ActionsDataEditor;
import view.editors.ConditionsDataEditor;
import view.editors.RowsDataEditor;

import java.util.List;

public class MinimumRequiredDataValidator extends TableValidator {

    private static final int MIN_ROWS_REQUIRED = 1;
    private static final int MIN_CONDITIONS_REQUIRED = 1;
    private static final int MIN_ACTIONS_REQUIRED = 1;

    public MinimumRequiredDataValidator(boolean strict) {
        super("Minimum required data validator", strict);
    }

    @Override
    public void run() {
        List<Row> rows = getDecisionTable().getRowData();
        List<Condition> conditions = getDecisionTable().getConditions();
        List<Action> actions = getDecisionTable().getActions();

        //verify we have enough of each
        if (MIN_ROWS_REQUIRED > 0) {
            if (rows == null || rows.isEmpty()) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(RowsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("Rows are required. There currently aren't any", validatorErrorLocation);
                addError(validatorError);
            }
            if (rows != null && rows.size() < MIN_ROWS_REQUIRED) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(RowsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("At least " + MIN_ROWS_REQUIRED + " rows are required. There currently are " + rows.size(), validatorErrorLocation);
                addError(validatorError);
            }
        }

        if (MIN_CONDITIONS_REQUIRED > 0) {
            if (conditions == null || conditions.isEmpty()) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(ConditionsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("Conditions are required. There currently aren't any.", validatorErrorLocation);
                addError(validatorError);
            }
            if (conditions != null && conditions.size() < MIN_CONDITIONS_REQUIRED) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(ConditionsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("At least " + MIN_CONDITIONS_REQUIRED + " conditions are required. There currently are " + conditions.size(), validatorErrorLocation);
                addError(validatorError);
            }
        }

        if (MIN_ACTIONS_REQUIRED > 0) {
            if (conditions == null || conditions.isEmpty()) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(ActionsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("Actions are required. There currently aren't any", validatorErrorLocation);
                addError(validatorError);
            }
            if (conditions != null && conditions.size() < MIN_ACTIONS_REQUIRED) {
                ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                validatorErrorLocation.setViewClass(ActionsDataEditor.class);
                ValidatorError validatorError = new ValidatorError("At least " + MIN_ACTIONS_REQUIRED + " actions are required. There currently are " + actions.size(), validatorErrorLocation);
                addError(validatorError);
            }
        }
    }
}
