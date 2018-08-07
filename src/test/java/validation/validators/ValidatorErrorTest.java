/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

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

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ValidatorErrorTest extends ApplicationTest {

    private ValidatorError validatorError;

    @Before
    public void setUp() {
        validatorError = new ValidatorError();
        validatorError.setMessage("Test message.");
        validatorError.setValidatorErrorLocation(new ValidatorErrorLocation());
    }

    @Test
    public void getValidatorErrorLocation() {
        assertNotNull(validatorError.getValidatorErrorLocation());
    }

    @Test
    public void setValidatorErrorLocation() {
        ValidatorErrorLocation validatorErrorLocation = new ValidatorErrorLocation();
        validatorErrorLocation.setRowNumber(56);
        validatorError.setValidatorErrorLocation(validatorErrorLocation);
        assertEquals(validatorErrorLocation, validatorError.getValidatorErrorLocation());
    }

    @Test
    public void getMessage() {
        assertEquals("Test message.", validatorError.getMessage());

    }

    @Test
    public void setMessage() {
        String expected = "A new test massage.";
        validatorError.setMessage(expected);
        assertEquals(expected, validatorError.getMessage());
    }
}