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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import validation.ValidationService;
import validation.validators.ValidatorError;

/**
 * Extension of JFXDialog providing a list of {@link ValidatorError} utilizing the {@link ValidationListCell}
 * to provide additional functionality.
 */
public class ValidationLogDialog extends JFXDialog {

    ListView<ValidatorError> validationLog;

    /**
     * Build a ValidationLogDialog with the current validation results and use the supplied StackPane as it's container.
     *
     * @param container the container for the dialog to appear in.
     */
    public ValidationLogDialog(StackPane container) {
        //standard properties
        setTransitionType(DialogTransition.BOTTOM);
        setDialogContainer(container);

        //build content
        AnchorPane content = new AnchorPane();
        content.setPrefSize(450, 300);
        content.getStyleClass().add("validationDialog");

        validationLog = new ListView<>();
        validationLog.setCellFactory(c -> new ValidationListCell());

        AnchorPane.setRightAnchor(validationLog, 0D);
        AnchorPane.setBottomAnchor(validationLog, 0D);
        AnchorPane.setLeftAnchor(validationLog, 0D);
        AnchorPane.setTopAnchor(validationLog, 0D);

        refreshLog();

        content.getChildren().setAll(validationLog);

        //add content to dialog
        setContent(content);
    }

    public void refreshLog(){
        ValidationService.getService().getLatestValidation().ifPresent(validation -> {
            validationLog.setItems(FXCollections.observableArrayList(validation.getAllErrors()));
        });
    }

}
