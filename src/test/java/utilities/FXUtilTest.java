/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package utilities;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FXUtilTest extends ApplicationTest {

    @Test
    public void runOnFXThread() {
        Runnable runnable = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        FXUtil.runOnFXThread(runnable::run);
    }

    @Test
    public void runAsync() {
        Runnable runnable = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        FXUtil.runAsync(runnable::run);
    }

    @Test
    public void installFade() {

        Pane testPane = new Pane();

        final FadeTransition fadeInTransition = FXUtil.installFade(testPane, FXUtil.AnimationFadeType.IN);
        assertNotNull(fadeInTransition);
        assertEquals(Duration.millis(200), fadeInTransition.getDuration());
        assertEquals(0, fadeInTransition.getFromValue(), 0);
        assertEquals(1, fadeInTransition.getToValue(), 0);
        assertEquals(testPane, fadeInTransition.getNode());
        fadeInTransition.play();

        final FadeTransition fadeOutTransition = FXUtil.installFade(testPane, FXUtil.AnimationFadeType.OUT);
        assertNotNull(fadeOutTransition);
        assertEquals(Duration.millis(200), fadeOutTransition.getDuration());
        assertEquals(1, fadeOutTransition.getFromValue(), 0);
        assertEquals(0, fadeOutTransition.getToValue(), 0);
        assertEquals(testPane, fadeOutTransition.getNode());
        fadeOutTransition.play();

    }

    @Test
    public void installBump() {

        Pane testPane = new Pane();
        final Timeline rightBump = FXUtil.installBump(testPane, FXUtil.AnimationDirection.RIGHT);
        assertNotNull(rightBump);
        assertEquals(2, rightBump.getKeyFrames().size(), 0);

        final Timeline leftBump = FXUtil.installBump(testPane, FXUtil.AnimationDirection.LEFT);
        assertNotNull(leftBump);
        assertEquals(2, leftBump.getKeyFrames().size(), 0);

        final Timeline UPBump = FXUtil.installBump(testPane, FXUtil.AnimationDirection.UP);
        assertNotNull(UPBump);
        assertEquals(2, UPBump.getKeyFrames().size(), 0);

        final Timeline DOWNBump = FXUtil.installBump(testPane, FXUtil.AnimationDirection.DOWN);
        assertNotNull(DOWNBump);
        assertEquals(2, DOWNBump.getKeyFrames().size(), 0);
    }

    @Test
    public void installBumpBack() {

        Pane testPane = new Pane();
        final Timeline rightBump = FXUtil.installBumpBack(testPane, FXUtil.AnimationDirection.RIGHT);
        assertNotNull(rightBump);
        assertEquals(2, rightBump.getKeyFrames().size(), 0);

        final Timeline leftBump = FXUtil.installBumpBack(testPane, FXUtil.AnimationDirection.LEFT);
        assertNotNull(leftBump);
        assertEquals(2, leftBump.getKeyFrames().size(), 0);

        final Timeline UPBump = FXUtil.installBumpBack(testPane, FXUtil.AnimationDirection.UP);
        assertNotNull(UPBump);
        assertEquals(2, UPBump.getKeyFrames().size(), 0);

        final Timeline DOWNBump = FXUtil.installBumpBack(testPane, FXUtil.AnimationDirection.DOWN);
        assertNotNull(DOWNBump);
        assertEquals(2, DOWNBump.getKeyFrames().size(), 0);
    }
}