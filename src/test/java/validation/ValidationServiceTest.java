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

import org.junit.Test;
import org.yari.core.YariException;

import static org.junit.Assert.*;

public class ValidationServiceTest {

    @Test
    public void getService() {
        assertNotNull(ValidationService.getService());
    }

    @Test
    public void validateXML() {
        boolean pass = false;
        try {
            ValidationService.getService().validateXML("<fail/>");
        } catch (YariException e) {
            pass = true;
        }

        assertTrue(pass);
    }

    @Test
    public void runValidationImmediately() {
        ValidationService.getService().runValidationImmediately();
    }

    @Test
    public void getLatestValidation() {
        assertNotNull(ValidationService.getService().getLatestValidation());
    }

    @Test
    public void requestValidation() {
        ValidationService.getService().requestValidation();
    }

    @Test
    public void validProperty() {
        assertNotNull(ValidationService.getService().validProperty());
    }

    @Test
    public void setEnabled() {
        boolean input = false;
        ValidationService.getService().setEnabled(input);
        assertEquals(input, ValidationService.getService().enabledProperty().getValue());
    }

    @Test
    public void busyProperty() {
        assertNotNull(ValidationService.getService().busyProperty());
    }

    @Test
    public void quickMessageProperty() {
        assertNotNull(ValidationService.getService().quickMessageProperty());
    }

    @Test
    public void setStrict() {
        boolean input = false;
        ValidationService.getService().setStrict(input);
    }

    @Test
    public void enabledProperty() {
        assertNotNull(ValidationService.getService().enabledProperty());
    }
}