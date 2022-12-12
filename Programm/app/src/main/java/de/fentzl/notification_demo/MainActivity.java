package de.fentzl.notification_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.Person;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Hauptklasse. Startet alle Unteraktivitäten
 * @author Simon Fentzl
 * @version 1
 */
public class MainActivity extends AppCompatActivity {
    private static final List<Message> MESSAGES = new ArrayList<>();
    private View mLayout;
    private static final int PERMISSION_REQUEST_NOT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mLayout = findViewById(R.id.include_content_main);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        //fab Button Einstellungen
        final FloatingActionButton create_remind = findViewById(R.id.fab_new_Notification);
        create_remind.setOnClickListener(v -> {
            Intent createNotIntent = new Intent(v.getContext(), CreateNotificationsOverview.class);
            startActivity(createNotIntent);
        });
        String annKey = "de.fentzl.anna";
        MESSAGES.add(new Message(getString(R.string.MessageMoring),new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1),null));
        String richardKey = "de.fentzl.richard";
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2),new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
        checkUserPermissions();
    }

    /**
     * Gibt die Liste der Nachrichten zurück
     * @return List<Message> Liste der Nachrichten
     */
    public static List<Message> getMessages() {
        return MESSAGES;
    }

    private void checkUserPermissions(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            return;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)){
            Snackbar.make(mLayout, R.string.AskPerm,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            PERMISSION_REQUEST_NOT);
                }
            }).show();
        }else{
            ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if(isGranted){
                    Log.d(NotificationDemoApplication.debugTag, "if in on create main Activity");
                }else {
                    Log.d(NotificationDemoApplication.debugTag, "else in on create main Activity");
                }
            });
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}