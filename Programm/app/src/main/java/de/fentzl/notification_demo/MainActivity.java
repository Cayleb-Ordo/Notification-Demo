package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.Person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Hauptklasse. Startet alle Unteraktivitäten
 * @author Simon Fentzl
 * @version 1
 */
public class MainActivity extends AppCompatActivity {
    public static final String debugTag = "NotDemo";
    private NotificationController notificationController;
    private final String annKey = "de.fentzl.anna";
    private final String richardKey = "de.fentl.richard";
    static List<Message> MESSAGES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MESSAGES.add(new Message(getString(R.string.MessageMoring),new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1),null));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2),new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
        notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannel(true);
        setContentView(R.layout.main_activity);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        /*
        fab Button Einstellungen
         */
        final FloatingActionButton create_remind = findViewById(R.id.fab_new_Notification);
        create_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createNotIntent = new Intent(v.getContext(), CreateNotificationsOverview.class);
                startActivity(createNotIntent);
            }
        });
    }
}