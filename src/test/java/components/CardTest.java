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

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import utilities.FXUtil;

import static org.testfx.assertions.api.Assertions.assertThat;

public class CardTest extends ApplicationTest {

    Card card;

    @Override
    public void start(Stage stage) throws Exception {
        card = new Card("Hello World");
        card.setId("card");
        Scene scene = new Scene(card, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void setDisplayedContent() {

        Label contentToDisplay = new Label("Test");
        contentToDisplay.setId("label");
        FXUtil.runOnFXThread(() -> card.setDisplayedContent(contentToDisplay));
        assertThat(card).hasChild("#label");
        assertThat(contentToDisplay).isVisible();

    }

    @Test
    public void setFooterContent() {

        Label footerContent = new Label("Footer");
        footerContent.setId("footer");
        FXUtil.runOnFXThread(() -> card.setFooterContent(footerContent));
        assertThat(footerContent).isVisible();

    }

}