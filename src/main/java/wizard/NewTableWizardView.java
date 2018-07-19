package wizard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import components.Card;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utilities.FXUtil;

import java.util.ArrayList;
import java.util.List;

public class NewTableWizardView extends Card {

    private final List<WizardStep> wizardSteps = new ArrayList<>();
    private final ObjectProperty<WizardStep> currentStep = new SimpleObjectProperty<>(null);
    private final StackPane transitionPane = new StackPane();
    private final StackPane contentPane = new StackPane();
    private final FadeTransition transitionOut = FXUtil.installFade(transitionPane, FXUtil.AnimationFadeType.OUT);
    private final FadeTransition transitionIn = FXUtil.installFade(transitionPane, FXUtil.AnimationFadeType.IN);


    public NewTableWizardView() {
        super();
        HBox buttonBar = new HBox(10);
        JFXButton nextButton = new JFXButton("NEXT");
        nextButton.getStyleClass().add("button-flat-gray");
        nextButton.setOnMouseClicked(me -> {

            if (getCurrentStep().isComplete) {
                if (wizardSteps.contains(getCurrentStep())) {
                    int index = wizardSteps.indexOf(getCurrentStep());
                    if (wizardSteps.size() <= index + 1) {
                        displayStep(wizardSteps.get(index + 1));
                        if (index + 1 == wizardSteps.size()) {
                            nextButton.setText("FINISH");
                        } else {
                            nextButton.setText("NEXT");
                        }
                    } else {
                        finish();
                    }
                }
            } else {
                //display incomplete error
            }

        });

        JFXButton previousButton = new JFXButton("PREVIOUS");
        previousButton.getStyleClass().add("button-flat-gray");
        previousButton.setOnMouseClicked(me -> {
            if (wizardSteps.contains(currentStep.get())) {
                int index = wizardSteps.indexOf(currentStep.get());
                if (index > 0) {
                    displayStep(wizardSteps.get(index - 1));
                }
            }
        });

        buttonBar.getChildren().addAll(previousButton, nextButton);
        setFooterContent(buttonBar);

        //setup tranisition pane
        transitionPane.setStyle("-fx-background-color: white;");
        transitionOut.setDuration(Duration.millis(1000));
        transitionIn.setDuration(Duration.millis(1000));

        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(25);
        contentPane.getChildren().setAll(loadingSpinner);
        setDisplayedContent(contentPane);

        FXUtil.runAsync(() -> {
            //TODO load all steps async

            FXUtil.runOnFXThread(() -> {
                //display first step
                displayStep(wizardSteps.get(0));
            });
        });

    }

    private void finish() {
        //TODO tidy
    }

    private void displayStep(WizardStep wizardStep) {
        setCurrentStep(wizardStep);
        transitionIn.play();
        contentPane.getChildren().setAll(wizardStep);
        transitionOut.play();
    }

    private void setCurrentStep(WizardStep wizardStep) {
        currentStep.setValue(wizardStep);
    }

    private WizardStep getCurrentStep() {
        return currentStep.getValue();
    }

}
