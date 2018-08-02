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

package settings;

import objects.RecommendedFile;
import objects.Theme;
import validation.ValidationType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple object describing several user-adjustable settings.
 */
public class Settings {

    //visual
    private Theme theme;

    //validation
    private ValidationType validationType;

    //project directory
    private String projectDirectory;

    //recent files
    private List<RecommendedFile> recommendedFiles;

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void addRecentFile(File file) {

        if (recommendedFiles == null) {
            recommendedFiles = new ArrayList<>();
        }

        RecommendedFile recommendedFile = new RecommendedFile(file);

        if (!recommendedFiles.contains(recommendedFile)) {
            if (recommendedFiles.size() >= 5) {
                RecommendedFile oldest = recommendedFiles.get(0);
                for (RecommendedFile rc : recommendedFiles) {
                    if (rc.getTime().before(oldest.getTime())) {
                        oldest = rc;
                    }
                }
                recommendedFiles.remove(oldest);
            }
            recommendedFiles.add(recommendedFile);
        }
    }

    public List<RecommendedFile> getRecommendedFiles() {
        return recommendedFiles;
    }
}
