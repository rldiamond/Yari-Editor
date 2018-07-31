/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package validation;

import validation.validators.DataTypeConversionValidator;
import validation.validators.MinimumRequiredDataValidator;
import validation.validators.TableInformationValidator;
import validation.validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    private List<Validator> validators = new ArrayList<>();
    private boolean valid;
    private boolean validityChecked = false;
    private boolean ran = false;
    private String quickMessage = "";

    public Validation(boolean isStrict) {
        //base validators
        validators.add(new TableInformationValidator(isStrict));
        validators.add(new MinimumRequiredDataValidator(isStrict));
        validators.add(new DataTypeConversionValidator(isStrict));

        //strict validators
        if (isStrict) {
            //..
        }
    }

    public boolean isValid() {
        if (!validityChecked && ran) {
            //only run this once.
            valid = !validators.stream()
                    .anyMatch(validator -> !validator.isValid());
            validityChecked = true;
        }
        return valid;
    }

    void run() {
        StringBuilder messageBuilder = new StringBuilder();
        validators.forEach(validator -> {
            validator.run();
            if (!validator.isValid()) {
                validator.getErrors().forEach(validatorError -> messageBuilder.append(validatorError.getMessage()));
            }
        });
        quickMessage = messageBuilder.toString();
        ran = true;
    }

}
