/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package excel;

import components.Card;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ExcelImportResultsCard extends StackPane {

    /**
     * Display the results of an Excel import based on the provided error messages.
     *
     * @param errorMessages the error messages (if any, can be null) found when importing Excel.
     */
    public ExcelImportResultsCard(List<String> errorMessages) {
        setPadding(new Insets(20, 20, 20, 20));
        Card card = new Card("Excel Import Complete");
        super.getChildren().setAll(card);

        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);

        if (errorMessages == null || errorMessages.isEmpty()) {
            Label success = new Label("The Excel file has been imported successfully. Select a Tool to the left to begin.");
            content.getChildren().setAll(success);
        } else {
            Label errors = new Label("The Excel file has been imported with errors. See the below list of error messages.");
            ListView<String> errorList = new ListView<>();
            errorList.setItems(FXCollections.observableArrayList(errorMessages));
            content.getChildren().setAll(errors, errorList);
        }
        card.setDisplayedContent(content);

    }

    /**
     * Displays an error card.
     *
     * @param ex the exception caught during Excel import.
     */
    public ExcelImportResultsCard(Throwable ex) {
        setPadding(new Insets(20, 20, 20, 20));
        Card card = new Card("Excel Import Failed");

        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);

        Label label = new Label("The Excel import failed. Check the file for invalid content.");
        TextArea exceptionMessage = new TextArea(ex.getMessage());
        exceptionMessage.setWrapText(true);
        VBox.setVgrow(exceptionMessage, Priority.ALWAYS);

        content.getChildren().setAll(label, exceptionMessage);
        card.setDisplayedContent(content);
    }

}
