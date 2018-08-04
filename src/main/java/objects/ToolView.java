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

import view.GeneralToolView;
import view.JavaCodeToolView;
import view.editors.ActionsToolView;
import view.editors.ConditionsToolView;
import view.editors.RowsToolView;

/**
 * Enumeration of different Tool views
 */
public enum ToolView {

    GENERAL(GeneralToolView.class),
    JAVA_CODE(JavaCodeToolView.class),
    ACTIONS(ActionsToolView.class),
    CONDITIONS(ConditionsToolView.class),
    ROWS(RowsToolView.class);

    private Class viewClass;

    ToolView(Class viewClass) {
        this.viewClass = viewClass;
    }

    /**
     * Returns the view class associated with the tool view.
     *
     * @return the view class associated with the tool view.
     */
    public Class getViewClass() {
        return viewClass;
    }
}
