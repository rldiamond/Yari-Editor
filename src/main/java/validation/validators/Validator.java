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

import java.util.List;

public interface Validator {

    /**
     * Returns true if the validation process succeeded, and false if it failed.
     *
     * @return true if the validation process succeeded, and false if it failed.
     */
    boolean isValid();

    /**
     * Returns a list of {@link ValidatorError} found during validation.
     *
     * @return a list of {@link ValidatorError} found during validation.
     */
    List<ValidatorError> getErrors();

    /**
     * Run the validation process.
     */
    void run();

    /**
     * Return the name of the Validator.
     *
     * @return the name of the Validator.
     */
    String getName();

}
