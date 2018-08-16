/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package excel;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Collections;

import static org.testfx.assertions.api.Assertions.assertThat;

public class ExcelImportResultsCardTest extends ApplicationTest {

    private ExcelImportResultsCard card;

    @Override
    public void start(Stage stage) throws Exception {
        card = new ExcelImportResultsCard(Collections.singletonList("Test"));
        stage.setScene(new Scene(card));
        stage.show();
    }

    @Test
    public void testFX() {
        assertThat(card).hasChild("The Excel file has been imported with errors. See the below list of error messages.");
    }
}