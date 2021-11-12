package de.fentzl.notification_demo;

import androidx.core.app.Person;

public class Message {
    private CharSequence text;
    private long timestamp;
    private Person sender;

    public Message(CharSequence text, Person sender){
        this.text = text;
        this.sender = sender;
        timestamp = System.currentTimeMillis();
    }

    public CharSequence getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Person getSender() {
        return sender;
    }
}
