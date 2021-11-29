package de.fentzl.notification_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Hauptklasse. Startet alle Unteraktivitäten
 * @author Simon Fentzl
 * @version 1
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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