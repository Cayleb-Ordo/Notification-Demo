package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Wird aufgerufen wenn ein Direct-Reply gedrückt wird
 * @author Simon Fentzl
 * @version 1
 */
public class ReplyActivity extends AppCompatActivity {
    private TextView txtV_Rply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

    }

}