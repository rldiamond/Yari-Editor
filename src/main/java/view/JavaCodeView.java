/*
 * This file is part of Yari Editor.
 *
 * Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Yari Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package view;

import com.jfoenix.controls.JFXButton;
import components.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import org.yari.core.table.DecisionTable;

import java.util.ArrayList;
import java.util.List;

public class JavaCodeView extends StackPane {

    private DecisionTable dt = RootLayoutFactory.getInstance().getDecisionTable();
    private String ruleName = RootLayoutFactory.getInstance().getRuleName();

    public JavaCodeView() {

        setPadding(new Insets(20, 20, 20, 20));

        Card card = new Card("Java Skeleton Code");

        TextArea codeDisplay = new TextArea();
        codeDisplay.setEditable(false);
        codeDisplay.setWrapText(true);

        String code = generateSkeletonCode();
        codeDisplay.setText(code);

        card.setDisplayedContent(codeDisplay);

        //copy button
        JFXButton copyButton = new JFXButton();
        copyButton.setText("COPY");
        copyButton.getStyleClass().add("button-flat-gray");

        copyButton.setOnMouseClicked(me -> copyToClipboard(code));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.getChildren().setAll(copyButton);

        card.setBottomContent(buttonBar);

        getChildren().add(card);

    }

    //TODO: Verify this output
    private String generateSkeletonCode(){
        StringBuilder sb = new StringBuilder();

        //imports
        sb.append("import java.io.FileNotFoundException;\n");
        sb.append("import org.yari.core.YariException;\n");
        sb.append("import org.yari.annotation.Action;\n");
        sb.append("import org.yari.annotation.Condition;\n");
        sb.append("import org.yari.core.BasicRule;\n");
        sb.append("import org.yari.core.Context;\n\n");

        // Create Class and Constructor
        sb.append("public class ").append(ruleName).append(" () extends BasicRule {\n    public ").append(ruleName).append(" throws FileNotFoundException, YariException {\n");
        sb.append("        super(\"").append(dt.getTableName()).append("\", \"").append(dt.getTableDescription()).append("\", \"XMLPATH\");\n    }\n\n");
        // Create lookupGloabals
        sb.append("    @Override\n    public void lookupGlobals(Context globalContext){\n    }\n\n");
        // Create condition methods
        List<String> conditionNames = new ArrayList<>();
        for (Condition condition : dt.getConditions()) {

            sb.append("    @Condition(\"").append(condition.getMethodName()).append("\")\n");
            sb.append("    public ").append(convertTypeToJava(condition.getDataType())).append(" ").append(condition.getMethodName()).append("(Context localContext){\n");
            sb.append("        return; // TODO: return a ").append(condition.getDataType()).append("\n    }\n\n");
        }
        // Create action methods
        for (Action action : dt.getActions()) {
            sb.append("    @Action(\"").append(action.getMethodName()).append("\")\n");
            sb.append("    public void ").append(action.getMethodName()).append("(Context actionContext){\n");
            sb.append("        // TODO: do something\n    }\n\n");
        }
        sb.append("}");


        return sb.toString();
    }

    private String convertTypeToJava(String dataType) {
        switch (dataType) {
            case "boolean":
                return "boolean";
            case "integer":
                return "int";
            case "string":
                return "String";
            case "double":
                return "double";
            case "char":
                return "char";
            case "byte":
                return "byte";
            case "short":
                return "short";
            case "long":
                return "long";
            case "float":
                return "float";
            default:
                return null;
        }
    }

    private void copyToClipboard(String code) {
        final Clipboard cb = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(code);
        cb.setContent(content);
    }
}
