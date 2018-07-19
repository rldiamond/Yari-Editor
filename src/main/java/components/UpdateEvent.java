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

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Custom Event used to send the call to the Validator to request validation be run.
 */
public class UpdateEvent extends Event {

    public static final EventType<UpdateEvent> UPDATE =
            new EventType<>(Event.ANY, "UPDATE");

    public UpdateEvent() {
        super(UPDATE);
    }

    public UpdateEvent(Object source, EventTarget target) {
        super(source, target, UPDATE);
    }

    @Override
    public UpdateEvent copyFor(Object newSource, EventTarget newTarget) {
        return (UpdateEvent) super.copyFor(newSource, newTarget);
    }

    @Override
    public EventType<? extends UpdateEvent> getEventType() {
        return (EventType<? extends UpdateEvent>) super.getEventType();
    }

}
