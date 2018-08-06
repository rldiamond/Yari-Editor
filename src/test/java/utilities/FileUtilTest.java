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

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.yari.core.table.DecisionTable;

import java.io.File;

import static org.junit.Assert.*;

public class FileUtilTest extends ApplicationTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void openFile() {
        FileUtil.openFile(new File(""), null);
    }

    @Test
    public void openFile1() {
        FXUtil.runOnFXThread(() -> FileUtil.openFile(null));
    }

    @Test
    public void newFile() {
        FileUtil.newFile();
        assertNull(FileUtil.getCurrentFile());
    }

    @Test
    public void saveToFile() {
        FileUtil.saveToFile(new File("/"));
    }

    @Test
    public void print() {
        FileUtil.print(new DecisionTable());
    }

    @Test
    public void getCurrentFile() {
        File file = FileUtil.getCurrentFile();
        assertNull(file);
    }

    @Test
    public void dirtyProperty() {
        boolean expected = false;
        boolean result = FileUtil.dirtyProperty().get();
        assertEquals(expected, result);
    }

    @Test
    public void setDirty() {
        boolean expected = !FileUtil.isDirty();
        FileUtil.setDirty(expected);
        boolean result = FileUtil.isDirty();
        assertEquals(expected, result);

    }

    @Test
    public void fileProperty() {
    }
}