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

import org.yari.core.table.Row;
import view.editors.RowsDataEditor;

import java.util.List;

/**
 * Determines if there are any non-unique table rows.
 */
public class UniqueRowValidator extends TableValidator {

    public UniqueRowValidator(boolean strict) {
        super("Unique row validator", strict);
    }

    @Override
    public void run() {
        List<Row> rows = getDecisionTable().getRawRowData();

        // Validate Rows
        for (Row currentRow : rows) {
            List<String> compare = currentRow.getValues();
            for (Row comparisonRow : rows) {
                if (currentRow == comparisonRow) {
                    continue;
                }
                // Check if rows are unique
                if (compare.equals(comparisonRow.getValues())) {
                    ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
                    validatorErrorLocation.setViewClass(RowsDataEditor.class);
                    validatorErrorLocation.setRow(comparisonRow);
                    String errMsg = "Two rows of data were found to have the same data! Check rows " + comparisonRow + " and " + currentRow + ".";
                    ValidatorError validatorError = new ValidatorError(errMsg, validatorErrorLocation);
                    addError(validatorError);
                }
            }
        }
    }
}
