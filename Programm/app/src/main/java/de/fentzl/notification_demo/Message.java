/*
 *     Copyright (C) 2021-2023 Simon Fentzl
 *     This file is part of Notification-Demo
 *
 *     Notification-Demo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Notification-Demo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.fentzl.notification_demo;

import androidx.core.app.Person;

/**
 * Message-Klasse, zum erstellen eines Messaging Style benötigt
 * @author Simon Fentzl
 * @version 1
 */
public class Message {
    private final CharSequence text;
    private final long timestamp;
    private final Person sender;

    public Message(CharSequence text, Person sender){
        this.text = text;
        this.sender = sender;
        timestamp = System.currentTimeMillis();
    }

    /**
     * Gibt den Text zurück
     * @return CharSequenz Text der Nachricht
     */
    public CharSequence getText() {
        return text;
    }

    /**
     * Gibt den Zeitstempel zurück
     * @return long Zeitstempel zum Zeitpunkt der Erstellung der Nachricht
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gibt die Person zurück, die die Nachricht geschrieben hat
     * @return Person Gibt das Person Objekt zurück
     */
    public Person getSender() {
        return sender;
    }
}
