package de.fentzl.notification_demo;

import android.app.Application;

import androidx.core.app.Person;

import java.util.ArrayList;
import java.util.List;

public class NotificationDemoApplication extends Application {
    public static final String debugTag = "NotDemo";
    private NotificationController notificationController;
    private final String annKey = "de.fentzl.anna";
    private final String richardKey = "de.fentl.richard";
    static List<Message> MESSAGES = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        MESSAGES.add(new Message(getString(R.string.MessageMoring),new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1),null));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2),new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
        notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannels(true);
    }
}
