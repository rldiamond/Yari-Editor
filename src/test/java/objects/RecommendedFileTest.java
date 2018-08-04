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

package objects;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class RecommendedFileTest {

    private RecommendedFile recommendedFile;
    private Calendar calendar;

    @Before
    public void prepareTest() {
        recommendedFile = new RecommendedFile(new File("/path"));
        recommendedFile.setName("test file");
        recommendedFile.setPath("/test/path");
        calendar = Calendar.getInstance();
        recommendedFile.setTime(calendar);
    }

    @Test
    public void getPath() {
        String path = recommendedFile.getPath();
        assertEquals("/test/path", path);
    }

    @Test
    public void setPath() {
        String input = "/new/path";
        recommendedFile.setPath(input);
        assertEquals(input, recommendedFile.getPath());
    }

    @Test
    public void getName() {
        String name = recommendedFile.getName();
        assertEquals("test file", name);
    }

    @Test
    public void setName() {
        String input = "new name";
        recommendedFile.setName(input);
        assertEquals(input, recommendedFile.getName());
    }

    @Test
    public void getTime() {
        Calendar time = recommendedFile.getTime();
        assertEquals(calendar, time);
    }

    @Test
    public void setTime() {
        Calendar input = Calendar.getInstance();
        recommendedFile.setTime(input);
        assertEquals(input, recommendedFile.getTime());
    }

    @Test
    public void equals() {

        boolean expected;
        boolean result;

        //test not same object
        int fail = 1;
        result = recommendedFile.equals(fail);
        expected = false;
        assertEquals(expected, result);

        //different name
        RecommendedFile differentFile = new RecommendedFile(new File("/path"));
        differentFile.setName("test file fail");
        differentFile.setPath("/test/path");
        differentFile.setTime(Calendar.getInstance());

        result = recommendedFile.equals(differentFile);
        assertEquals(expected, result);

        //different path
        differentFile.setName("test file");
        differentFile.setPath("/diff/path");
        result = recommendedFile.equals(differentFile);
        assertEquals(expected, result);

        //same
        differentFile.setPath("/test/path");
        result = recommendedFile.equals(differentFile);
        expected = true;
        assertEquals(expected, result);

    }
}