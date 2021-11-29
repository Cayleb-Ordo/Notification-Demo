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
