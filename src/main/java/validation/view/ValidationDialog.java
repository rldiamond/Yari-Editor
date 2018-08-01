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

/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package validation.view;

import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import validation.ValidationService;

public class ValidationDialog extends JFXDialog {

    public ValidationDialog(StackPane container) {
        //standard properties
        setTransitionType(DialogTransition.BOTTOM);
        setDialogContainer(container);

        //build content
        AnchorPane content = new AnchorPane();
        content.setPrefSize(450, 300);
        content.getStyleClass().add("validationDialog");

        ValidationListView validationListView = new ValidationListView();

        AnchorPane.setRightAnchor(validationListView, 0D);
        AnchorPane.setBottomAnchor(validationListView, 0D);
        AnchorPane.setLeftAnchor(validationListView, 0D);
        AnchorPane.setTopAnchor(validationListView, 0D);

        ValidationService.getService().getLatestValidation().ifPresent(validation -> {
            validationListView.setItems(FXCollections.observableArrayList(validation.getAllErrors()));
        });

        content.getChildren().setAll(validationListView);

        //add content to dialog
        setContent(content);
    }

}
