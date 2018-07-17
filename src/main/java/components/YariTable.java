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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.yari.core.table.Action;
import org.yari.core.table.Condition;
import view.RootLayoutFactory;

import java.util.List;

/**
 * Abstract TableView to provide common formatting options throughout the application.
 *
 * @param <T> the object the tableview is based upon.
 */
public abstract class YariTable<T extends Object> extends TableView<T> {

    public static final ObservableList<String> dataTypeValues = FXCollections.observableArrayList("boolean", "byte", "char", "double", "float", "integer", "long", "short", "string");
    public static final ObservableList<String> comparatorValues = FXCollections.observableArrayList("==", "!=", "GT", "GE", "LT", "LE");
    public static final ObservableList<String> boolOptions = FXCollections.observableArrayList("True", "False");
    protected final ObservableList<Condition> conditions = RootLayoutFactory.getInstance().getConditionsList();
    protected final ObservableList<Action> actions = RootLayoutFactory.getInstance().getActionsList();

    public YariTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setEditable(true);

        List<TableColumn<T, ?>> columns = buildColumns();
        getColumns().addAll(columns);
    }

    protected abstract List<TableColumn<T, ?>> buildColumns();

}
