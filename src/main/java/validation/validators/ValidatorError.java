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

public class ValidatorError {

    private String message;
    private ValidatorErrorLocation validatorErrorLocation;

    public ValidatorError() {

    }

    public ValidatorError(String message) {
        this.message = message;
    }

    public ValidatorError(String message, ValidatorErrorLocation validatorErrorLocation) {
        this.message = message;
        this.validatorErrorLocation = validatorErrorLocation;
    }

    public ValidatorErrorLocation getValidatorErrorLocation() {
        return validatorErrorLocation;
    }

    public void setValidatorErrorLocation(ValidatorErrorLocation validatorErrorLocation) {
        this.validatorErrorLocation = validatorErrorLocation;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
