/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package components.wizard;

import javafx.scene.layout.StackPane;

public abstract class WizardStep extends StackPane {

    /**
     * Determines if the wizard step is complete. This method should run data validations to ensure the user-entered
     * data is correct and sufficient to move on to the next step.
     *
     * @return true if the step is complete, false if not.
     */
    public abstract boolean isComplete();

    /**
     * This method should return a message detailing the reasons why a wizard step is currently incomplete.
     * This method is called if {@link WizardStep#isComplete() returns FALSE to display a dialog to the user.
     *
     * @return a detailed message explaining why the step is currently incomplete.
     */
    public abstract String getIncompleteMessage();
}
