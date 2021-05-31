package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * Diese Klasse setzt je nach Benuztereingabe eine Bestimmte Notification
 * @author Simon Fentzl
 * @version 1
 */
public class CreateNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);
         /*
        Toolbar-Einstellungen
         */

        Toolbar toolbar = findViewById(R.id.main_appbar);
        if (toolbar == null)
            Log.d(MainActivity.debugTag,"Toolbar wurde nicht erstellt");
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}