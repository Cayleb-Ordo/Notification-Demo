package de.fentzl.notification_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
/**
 * Wird aufgerufen wenn auf die Push-Nachricht geklickt wird
 * @author Simon Fentzl
 * @version 1
 */

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
    }
}