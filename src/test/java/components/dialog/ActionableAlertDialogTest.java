/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components.dialog;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

public class ActionableAlertDialogTest extends ApplicationTest {
    private ActionableAlertDialog dialog;

    private boolean didFunction = false;

    @Override
    public void start(Stage stage) throws Exception {
        dialog = new ActionableAlertDialog(AlertDialogType.CONFIRMATION, stage.getOwner());
        dialog.setTitle("Test Title");
        dialog.setBody("Test Body");
        dialog.show();
    }

    @Test
    public void testFX() {
        assertThat(dialog).hasChild("Test Title");
        assertThat(dialog).hasChild("Test Body");
        assertThat(dialog).hasChild("DISMISS");
    }

    @Test
    public void setAction() {
        dialog.setAction(() -> didFunction = true);
        clickOn("OKAY");
        assertTrue(didFunction);
    }

    @Test
    public void setActionButtonText() {
        Platform.runLater(() -> {
            dialog.setActionButtonText("TEST");
            assertThat(dialog).hasChild("TEST");
        });
    }
}