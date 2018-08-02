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

import org.yari.core.table.DecisionTable;
import utilities.TableUtil;
import view.RootLayout;
import view.RootLayoutFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TableValidator implements Validator {

    private final DecisionTable decisionTable;
    private final String validatorName;
    private final boolean strict;
    private List<ValidatorError> errors;

    public TableValidator(String validatorName, boolean strict) {
        // update the table with the latest data
        TableUtil.updateTable();
        final RootLayout rootLayout = RootLayoutFactory.getInstance();
        final DecisionTable decisionTable = rootLayout.getDecisionTable();
        this.decisionTable = decisionTable;
        this.validatorName = validatorName;
        this.strict = strict;
    }

    /**
     * Retrieve the current {@link DecisionTable}.
     *
     * @return the current {@link DecisionTable}.
     */
    protected DecisionTable getDecisionTable() {
        return decisionTable;
    }

    /**
     * Add an error to the validator.
     *
     * @param error the error to add.
     */
    protected void addError(ValidatorError error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        if (error == null) {
            return;
        }
        errors.add(error);
    }

    /**
     * Get the strict setting for the validator. Used for some validators who may validate slightly
     * different in strict mode.
     *
     * @return the strict setting for the validator.
     */
    protected boolean isStrict() {
        return strict;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isValid() {
        return errors == null || errors.isEmpty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<ValidatorError> getErrors() {
        if (errors == null) {
            return Collections.emptyList();
        }
        return errors;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return validatorName;
    }
}
