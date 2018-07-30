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

/**
 * Currently implemented themes and their corresponding CSS path.
 */
public enum Theme {
    DARK("theme/dark.css");

    private final String css;

    Theme(String css) {
        this.css = css;
    }

    /**
     * Return the CSS path.
     *
     * @return the CSS path.
     */
    public String getCss() {
        return css;
    }
}