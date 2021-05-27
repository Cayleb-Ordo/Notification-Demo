package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String debugTag = "NotDemo";
    private NotificationController notificationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannel();
        setContentView(R.layout.main_activity);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
    }
}