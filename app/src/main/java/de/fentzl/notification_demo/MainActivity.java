package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String debugTag = "NotDemo";
    private Intent creminderoverviewIntent;
    private NotificationController notificationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannel();
        setContentView(R.layout.main_activity);
        notificationController.setStartNotification();
    }
}