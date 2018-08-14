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
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package components;

import com.jfoenix.transitions.JFXFillTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utilities.FXUtil;

/**
 * Custom implementation of HBox to create uniform 'buttons' for the SideBar.
 */
public class MenuOption extends StackPane {
    //notes: HBox [ {icon}   {titleProperty} ]

    private StringProperty titleProperty = new SimpleStringProperty("");
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    private Runnable runnable;

    public MenuOption(String title, String iconID) {
        titleProperty.setValue(title);
        setPadding(new Insets(0, 0, 0, 10));
        setId("mdTab");
        super.setPrefSize(USE_COMPUTED_SIZE, 50);
        setAlignment(Pos.CENTER_LEFT);

        //button label
        Label label = new Label();
        label.textProperty().bind(titleProperty);

        //icon
        Pane icon = new Pane();
        icon.getStyleClass().add("mdTab-icon");
        icon.setId(iconID);
        icon.setPrefSize(12, 12);
        icon.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        HBox wrapper = new HBox(5);
        wrapper.setAlignment(Pos.CENTER_LEFT);
        wrapper.getChildren().setAll(icon, label);

        //custom animations when selected and unselected
        JFXFillTransition selectedTransition = new JFXFillTransition();
        selectedTransition.setDuration(Duration.millis(100));
        selectedTransition.setFromValue(Color.TRANSPARENT);
        selectedTransition.setToValue(Color.ROSYBROWN);
        selectedTransition.setRegion(this);

        JFXFillTransition unselectedTransition = new JFXFillTransition();
        unselectedTransition.setDuration(Duration.millis(100));
        unselectedTransition.setFromValue(Color.ROSYBROWN);
        unselectedTransition.setToValue(Color.TRANSPARENT);
        unselectedTransition.setRegion(this);

        //hover animate
        Timeline popOut = FXUtil.installBump(wrapper, FXUtil.AnimationDirection.RIGHT);
        Timeline popBack = FXUtil.installBumpBack(wrapper, FXUtil.AnimationDirection.RIGHT);

        setOnMouseEntered(me -> {
            popBack.stop();
            popOut.play();
        });

        setOnMouseExited(me -> {
            popOut.stop();
            popBack.play();
        });

        //custom css pseudoclass when selected
        PseudoClass selected = PseudoClass.getPseudoClass("selected");

        selectedProperty.addListener((obs, wasSelected, isSelected) -> {
            pseudoClassStateChanged(selected, isSelected); //trigger pseudoclass update
            if (isSelected) {
                selectedTransition.play();
                if (runnable != null) {
                    runnable.run();
                }
            } else if (wasSelected && !isSelected) {
                unselectedTransition.play();
            }
        });

        super.getChildren().setAll(wrapper);

    }

    public void setOnSelectAction(Runnable runnable) {
        this.runnable = runnable;
    }

    public void select() {
        selectedProperty.setValue(true);
    }

    public void deselect() {
        selectedProperty.setValue(false);
    }

    public String getTitle() {
        return titleProperty.get();
    }

}
