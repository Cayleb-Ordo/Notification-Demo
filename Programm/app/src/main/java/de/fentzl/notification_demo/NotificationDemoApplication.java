package de.fentzl.notification_demo;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.Person;

import java.util.ArrayList;
import java.util.List;

public class NotificationDemoApplication extends Application {
    public static final String debugTag = "NotDemo";
    public static Bitmap rwu_logo;
    public static Bitmap mausi_logo;
    private NotificationController notificationController;
    private final String annKey = "de.fentzl.anna";
    private final String richardKey = "de.fentl.richard";
    static List<Message> MESSAGES = new ArrayList<>();
    static NotificationBuilder notBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        notBuilder = new NotificationBuilder(getApplicationContext());
        rwu_logo = BitmapFactory.decodeResource(getResources(), R.drawable.rwu_logo);
        mausi_logo = BitmapFactory.decodeResource(getResources(), R.drawable.mausi);
        MESSAGES.add(new Message(getString(R.string.MessageMoring),new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1),null));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2),new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
        notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannels(true);
    }
}
