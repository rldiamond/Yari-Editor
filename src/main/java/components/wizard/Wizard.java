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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Wizard {

    private List<WizardStep> wizardSteps = new ArrayList<>();

    /**
     * Build the Wizard with the provided steps.
     *
     * @param wizardSteps the WizardSteps to include in the Wizard (in order).
     */
    protected Wizard(WizardStep... wizardSteps) {
        Collections.addAll(this.wizardSteps, wizardSteps);
    }
}
