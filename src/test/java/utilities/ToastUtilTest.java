/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import view.RootLayoutFactory;

import static org.junit.Assert.*;

public class ToastUtilTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = RootLayoutFactory.getScene();
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void sendPersistentToast() {
        ToastUtil.sendToast("Test Persistent toast!");
    }

    @Test
    public void sendToast() {
        ToastUtil.sendToast("Test toast!");
    }
}