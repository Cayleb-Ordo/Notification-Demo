package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

/**
 * Diese Klasse setzt je nach Benuztereingabe eine Bestimmte Notification
 * @author Simon Fentzl
 * @version 1
 */
public class CreateNotificationsOverview extends AppCompatActivity {
    private NotificationController notificationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationController = new NotificationController(this.getApplicationContext());
        setContentView(R.layout.activity_create_notifications_overview);
        /*
        Toolbar-Einstellungen
         */
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     *
     */
    public void onChannel1ButtonCLicked(View view){
        notificationController.notifyChannel1();
    }

    /**
     *
     */
    public void onChannel2ButtonClicked(View view){
        notificationController.notifyChannel2();
    }
}
