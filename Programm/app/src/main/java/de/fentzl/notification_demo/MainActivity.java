/*
 *     Copyright (C) 2021-2023 Simon Fentzl
 *     This file is part of Notification-Demo
 *
 *     Notification-Demo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Notification-Demo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Notification-Demo.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.fentzl.notification_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Hauptklasse, startet alle Unteraktivitäten
 *
 * @author Simon Fentzl
 * @version 2.5
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
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
        new MenuInflater(this).inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName())));
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragt die benötigten Berechtigungen an
     */
    public void requestPermissions() {
        //Wird nur aufgerufen, wenn der Benutzer einmal auf nicht erlauben gedrückt hat. Bei zweimaligem Verneinen wird angenommen, das nicht noch einmal nachgefragt werden soll.
       if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               new NotificationRationaleDialog().show(getSupportFragmentManager(), "rationaleDialog");
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