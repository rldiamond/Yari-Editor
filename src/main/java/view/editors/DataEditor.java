/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package view.editors;

public interface DataEditor {

    /**
     * Remove the selected row from the data editor table.
     */
    void removeSelectedRow();

    /**
     * Add a new row to the data editor table.
     */
    void addNewRow();

    /**
     * Move the selected row up in the data editor table.
     */
    void moveRowUp();

    /**
     * Move the selected row down in the data editor table.
     */
    void moveRowDown();

}
