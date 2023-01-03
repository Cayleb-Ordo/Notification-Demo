package de.fentzl.notification_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * Hauptklasse. Startet alle Unteraktivitäten
 *
 * @author Simon Fentzl
 * @version 2
 */
public class MainActivity extends AppCompatActivity {
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(NotificationDemoApplication.debugTag, NotificationDemoApplication.getAPPLICATION().getApplicationContext().getPackageName());
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
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i(NotificationDemoApplication.debugTag, "Berechtigt!");
        } else{
            requestPermissions();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.not_popupmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Fragt die benötigten Berechtigungen an
     */
    public void requestPermissions() {
        //PermissionRationale wir das erste mal aufgerufen, wenn der Benutzer einmal auf nicht erlauben getrückt hat. Bei zweimaligem verneinen wird angenommen, das nicht nocheinmal nachgefragt werden soll
       if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
/*               Snackbar.make(mLayout, R.string.AskPerm, Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, view -> {
                   Intent tmp = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                   tmp.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                   startActivity(tmp);
               }).show();*/
               new NotificationRationaleDialog().show(getSupportFragmentManager(), "testDialog");
           }
       } else {
            ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i(NotificationDemoApplication.debugTag, "Berechtigung erteilt!");
                } else {
                    Log.i(NotificationDemoApplication.debugTag, "Berechtigung nicht erteilt");
                }
            });
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
           }
       }
    }
}