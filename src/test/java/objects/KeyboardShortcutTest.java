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

package objects;

import javafx.scene.input.KeyCombination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class KeyboardShortcutTest {

    @Mock
    Runnable action;

    @InjectMocks
    KeyboardShortcut shortcut = KeyboardShortcut.NEW;

    @Test
    public void runShortcutAction() {

        Mockito.doNothing().when(action).run();
        shortcut.runShortcutAction();
        Mockito.verify(action, Mockito.times(1)).run();

    }

    @Test
    public void getKeyCombination() {

        List<KeyCombination> duplicateFinder = new ArrayList<>();

        Arrays.stream(KeyboardShortcut.values()).forEach(keyboardShortcut -> {

            assertNotNull(keyboardShortcut.getKeyCombination());
            assertTrue(!duplicateFinder.contains(keyboardShortcut.getKeyCombination()));
            duplicateFinder.add(keyboardShortcut.getKeyCombination());

        });

    }

    @Test
    public void getDisplayLabel() {

        String startsWith = "Ctrl+";

        Arrays.stream(KeyboardShortcut.values()).forEach(keyboardShortcut -> assertTrue(keyboardShortcut.getDisplayLabel().startsWith(startsWith)));

    }
}