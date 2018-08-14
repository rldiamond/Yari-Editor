/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */
package validation.validators;

import objects.ToolView;
import org.yari.core.table.TableAction;
import org.yari.core.table.TableCondition;
import org.yari.core.table.TableRow;

public class ValidatorErrorLocation {

    private TableRow row;
    private TableCondition condition;
    private TableAction action;
    private int rowNumber;
    private int columnNumber;
    private ToolView toolView;

    public ToolView getToolView() {
        return toolView;
    }

    public void setToolView(ToolView toolView) {
        this.toolView = toolView;
    }

    public TableRow getTableRow() {
        return row;
    }

    public void setTableRow(TableRow row) {
        this.row = row;
    }

    public TableCondition getTableCondition() {
        return condition;
    }

    public void setTableCondition(TableCondition condition) {
        this.condition = condition;
    }

    public TableAction getAction() {
        return action;
    }

    public void setTableAction(TableAction action) {
        this.action = action;
    }

    public int getTableRowNumber() {
        return rowNumber;
    }

    public void setTableRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getTableColumnNumber() {
        return columnNumber;
    }

    public void setTableColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
