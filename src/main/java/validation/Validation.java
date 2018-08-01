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

import validation.validators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Validation object allows for multiple validators to be ran and for inspections to be easily made
 * against the results of each validator.
 */
public class Validation {

    private List<Validator> validators = new ArrayList<>();
    private boolean valid;
    private boolean validityChecked = false;
    private boolean ran = false;
    private String quickMessage;

    /**
     * Construct a Validation object with basic {@link Validator}s configured with the supplied strict setting.
     *
     * @param isStrict should the {@link Validator} be ran in strict mode.
     */
    public Validation(boolean isStrict) {
        validators.add(new TableInformationValidator(isStrict));
        validators.add(new MinimumRequiredDataValidator(isStrict));
        validators.add(new DataTypeConversionValidator(isStrict));
        validators.add(new UniqueRowValidator(isStrict));
    }

    /**
     * Returns true if all validators have been ran and are valid, false if else.
     *
     * @return true if all validators have been ran and are valid, false if else.
     */
    public boolean isValid() {
        if (!validityChecked && ran) {
            //only run this once.
            valid = !validators.stream()
                    .anyMatch(validator -> !validator.isValid());
            validityChecked = true;
        }
        return valid;
    }

    /**
     * Run the validation process.
     */
    void run() {
        StringBuilder messageBuilder = new StringBuilder();
        validators.forEach(validator -> {
            validator.run();
            if (!validator.isValid()) {
                validator.getErrors().forEach(validatorError -> messageBuilder.append(validatorError.getMessage()).append(". \n"));
            }
        });
        quickMessage = messageBuilder.toString();
        ran = true;
    }

    /**
     * Return the quick message built from the validation process.
     *
     * @returnt he quick message built from the validation process.
     */
    public String getQuickMessage() {
        return quickMessage;
    }

    /**
     * Returns all errors from all validators ran.
     *
     * @return all errors from all validators ran.
     */
    public List<ValidatorError> getAllErrors() {
        return validators.stream()
                .flatMap(validator -> validator.getErrors().stream())
                .collect(Collectors.toList());
    }

}
