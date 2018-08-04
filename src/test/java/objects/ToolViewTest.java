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

import org.junit.jupiter.api.Test;
import view.GeneralToolView;
import view.JavaCodeToolView;
import view.editors.ActionsToolView;
import view.editors.ConditionsToolView;
import view.editors.RowsToolView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToolViewTest {

    @Test
    void getViewClass() {

        Class expected;
        Class result;

        //general
        expected = GeneralToolView.class;
        result = ToolView.GENERAL.getViewClass();
        assertEquals(expected, result);

        //java code
        expected = JavaCodeToolView.class;
        result = ToolView.JAVA_CODE.getViewClass();
        assertEquals(expected, result);

        //actions
        expected = ActionsToolView.class;
        result = ToolView.ACTIONS.getViewClass();
        assertEquals(expected, result);

        //conditions
        expected = ConditionsToolView.class;
        result = ToolView.CONDITIONS.getViewClass();
        assertEquals(expected, result);

        //rows
        expected = RowsToolView.class;
        result = ToolView.ROWS.getViewClass();
        assertEquals(expected, result);
        
    }
}