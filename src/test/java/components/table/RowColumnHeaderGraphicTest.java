/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components.table;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.yari.core.table.TableCondition;

import static org.testfx.assertions.api.Assertions.assertThat;


public class RowColumnHeaderGraphicTest extends ApplicationTest {

    private RowColumnHeaderGraphic graphic;

    @Override
    public void start(Stage stage) throws Exception {
        TableCondition tableCondition = new TableCondition();
        tableCondition.setName("Test Name");
        tableCondition.setMethodName("Test Method Name");
        tableCondition.setDataType("boolean");
        tableCondition.setComparator("GT");
        graphic = new RowColumnHeaderGraphic(tableCondition);
        stage.setScene(new Scene(graphic));
        stage.show();
    }


    @Test
    public void testFX() {
        RowColumnHeaderGraphic.setSticky(true);

        assertThat(graphic).hasChild("Test Name");
        Label titleLabel = lookup("Test Name").query();
        Label subtextLabel = lookup("boolean, >").query();

        assertThat(titleLabel).isVisible();
        assertThat(subtextLabel).isVisible();

        RowColumnHeaderGraphic.setSticky(false);

        assertThat(titleLabel).isVisible();
        assertThat(subtextLabel).isInvisible();

    }
}