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

import validation.validators.TestValidator;
import validation.validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    private List<Validator> validators = new ArrayList<>();
    boolean valid;
    boolean validityChecked = false;

    public Validation(boolean isStrict) {
        //base validators
        validators.add(new TestValidator());

        //strict validators
        if (isStrict) {
            //..
        }
    }

    public boolean isValid() {
        if (!validityChecked) {
            valid = validators.stream()
                    .anyMatch(validator -> !validator.isValid());
        }
        return valid;
    }

    void run() {
        validators.forEach(Validator::run);
    }

}
