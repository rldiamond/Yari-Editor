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

import com.jfoenix.controls.JFXButton;
import components.Card;
import components.dialog.AlertDialogType;
import components.dialog.NonActionableAlertDialog;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Wizard<T extends Object> extends Card {

    private List<WizardStep> wizardSteps = new ArrayList<>();
    private T data;
    private JFXButton nextButton;
    private JFXButton previousButton;

    /**
     * Build the Wizard with the provided steps.
     *
     * @param name the name to display in the Wizard.
     * @param data data to pass around.
     * @param wizardSteps the WizardSteps to include in the Wizard (in order).
     */
    protected Wizard(String name, T data, WizardStep... wizardSteps) {
        super(name);
        this.data = data;
        Collections.addAll(this.wizardSteps, wizardSteps);

        initializeWizard();
    }

    private void initializeWizard() {
        //setup buttons
        nextButton = new JFXButton("NEXT");
        nextButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                handleNext();
            }
        });
        Tooltip.install(nextButton, new Tooltip("Proceed"));
        nextButton.getStyleClass().add(".button-flat-gray");

        if (wizardSteps.size() == 1) {
            nextButton.setText("COMPLETE");
        }

        previousButton = new JFXButton("PREVIOUS");
        Tooltip.install(previousButton, new Tooltip("Go back to previous step"));
        previousButton.getStyleClass().add(".button-flat-gray");
        previousButton.setDisable(true);
        previousButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.SECONDARY)) {
                handlePrevious();
            }
        });
        HBox buttonBar = new HBox(previousButton, nextButton);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setPrefHeight(45);
        buttonBar.setMinHeight(USE_PREF_SIZE);
        buttonBar.setMaxHeight(USE_PREF_SIZE);
        buttonBar.setSpacing(10);
        super.setFooterContent(buttonBar);

        //display first step
        super.setDisplayedContent(wizardSteps.get(0));


        //todo: show a counter of steps ie 3/6, tooltips
    }

    private void handleNext() {
        getDisplayedContent().ifPresent(node -> {
            if (!(node instanceof WizardStep)) {
                return;
            }

            WizardStep wizardStep = (WizardStep) node;

            int index = wizardSteps.indexOf(wizardStep);

            if (index < wizardSteps.size()) {
                if (wizardStep.isComplete()) {
                    super.setDisplayedContent(wizardSteps.get(index++));
                } else {
                    //TODO SHOW DIALOG
                    NonActionableAlertDialog incompleteDialog = new NonActionableAlertDialog(AlertDialogType.ERROR);
                    incompleteDialog.setTitle("Step Is Incomplete");
                    incompleteDialog.setBody(wizardStep.getIncompleteMessage());
                    incompleteDialog.show();
                    return;
                }
            }

            if (index == wizardSteps.size()) {
                nextButton.setText("COMPLETE");
            } else {
                nextButton.setText("NEXT");
            }

        });
    }

    private void handlePrevious() {
        getDisplayedContent().ifPresent(node -> {
            if (!(node instanceof WizardStep)) {
                return;
            }

            WizardStep wizardStep = (WizardStep) node;

            int index = wizardSteps.indexOf(wizardStep);

            if (index < wizardSteps.size()) {
                super.setDisplayedContent(wizardSteps.get(index--));
            }

            if (index == 0) {
                previousButton.setDisable(true);
            } else {
                previousButton.setDisable(false);
            }

        });
    }


    /**
     * Returns the object being built in the Wizard.
     *
     * @return the object being built in the Wizard.
     */
    public T getData() {
        return data;
    }

}
