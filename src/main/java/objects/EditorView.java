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

import view.GeneralView;
import view.JavaCodeView;
import view.editors.ActionsDataEditor;
import view.editors.ConditionsDataEditor;
import view.editors.RowsDataEditor;

public enum EditorView {

    GENERAL(GeneralView.class),
    SKELETON_CODE(JavaCodeView.class),
    ACTIONS_EDITOR(ActionsDataEditor.class),
    CONDITIONS_EDITOR(ConditionsDataEditor.class),
    ROWS_EDITOR(RowsDataEditor.class);

    private Class viewClass;

    private EditorView(Class viewClass) {
        this.viewClass = viewClass;
    }

    public Class getViewClass() {
        return viewClass;
    }
}
