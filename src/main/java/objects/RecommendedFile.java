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

import java.io.File;
import java.util.Calendar;

public class RecommendedFile {

    private String path;
    private String name;
    private Calendar time;

    public RecommendedFile(File file) {
        time = Calendar.getInstance();
        path = file.getPath();
        name = file.getName();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecommendedFile)){
            return false;
        }
        RecommendedFile other = (RecommendedFile) obj;

        if (!other.getName().equalsIgnoreCase(getName())){
            return false;
        }

        if (!other.getPath().equalsIgnoreCase(getPath())){
            return false;
        }

        return true;

    }
}
