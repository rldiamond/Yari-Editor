/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class MenuOptionTest extends ApplicationTest {

    MenuOption menuOption;


    @Override
    public void start(Stage stage) throws Exception {
        menuOption = new MenuOption("test", "test");
        Scene scene = new Scene(menuOption);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void setOnSelectActionAndSelect() {
        MenuOption menuOption = new MenuOption("test", "test");
        Runnable test = Mockito.mock(Runnable.class);
        Mockito.doNothing().when(test).run();
        menuOption.setOnSelectAction(test);

        //see if it set
        Mockito.verify(test, Mockito.times(0)).run();
        menuOption.select();
        Mockito.verify(test, Mockito.times(1)).run();
    }

    @Test
    public void deselect() {
        MenuOption menuOption = new MenuOption("test", "test");
        menuOption.deselect();
    }

    @Test
    public void getTitle() {
        assertEquals("test", menuOption.getTitle());
    }
}