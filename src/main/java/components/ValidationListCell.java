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

package components;

import javafx.scene.control.ListCell;
import validation.validators.ValidatorError;
import view.RootLayoutFactory;

public class ValidationListCell extends ListCell<ValidatorError> {

    public ValidationListCell() {

        //TODO: Custom graphic
        setOnMouseClicked(me -> {
            if (me.getClickCount() == 2){
                // show the appropriate view
                if (getItem() != null && getItem().getValidatorErrorLocation() != null) {
                    RootLayoutFactory.getInstance().showView(getItem().getValidatorErrorLocation().getEditorView());
                }
            }
        });

    }

    @Override
    protected void updateItem(ValidatorError item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getMessage());
        }
    }
}
