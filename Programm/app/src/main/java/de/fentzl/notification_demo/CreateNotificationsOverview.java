/*
 *     Copyright (C) 2021-2024 Simon Fentzl
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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

/**
 * Diese Klasse setzt je nach Benutzereingabe eine bestimmte Notification
 *
 * @author Simon Fentzl
 * @version 1
 */
public class CreateNotificationsOverview extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private final String CLASS_CreateNotificationsOverview = "de.fentzl.notification_demo.CreateNotificationsOverview";
    private NotificationController notificationController;
    private boolean button1Clicked = false;

    /**
     * Enumeration für die unterschiedlichen Typen von Notifications
     */
    public enum NotificationType {
        Default,
        Progress,
        BigPicture,
        BigText,
        Custom,
        Media,
        Reply
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationController = NotificationDemoApplication.getAPPLICATION().getNotController();
        setContentView(R.layout.activity_create_not_overv);
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    // evtl auf view-binding umstellen damit oder auf if else wechseln
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent tmpInt = new Intent(CreateNotificationsOverview.this, MainActivity.class);
        int id = item.getItemId();
        if (id == R.id.Pop_Default) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.Default);
            else
                notificationController.notifyChannel2(NotificationType.Default);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_Progress) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.Progress);
            else
                notificationController.notifyChannel2(NotificationType.Progress);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_BigPicture) {

            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.BigPicture);
            else
                notificationController.notifyChannel2(NotificationType.BigPicture);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_BigText) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.BigText);
            else
                notificationController.notifyChannel2(NotificationType.BigText);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_Media) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.Media);
            else
                notificationController.notifyChannel2(NotificationType.Media);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_Rply) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.Reply);
            else
                notificationController.notifyChannel2(NotificationType.Reply);
            startActivity(tmpInt);
            return true;
        } else if (id == R.id.Pop_Custom) {
            if (button1Clicked)
                notificationController.notifyChannel1(NotificationType.Custom);
            else
                notificationController.notifyChannel2(NotificationType.Custom);
            startActivity(tmpInt);
            return true;
        } else {
            Log.d(NotificationDemoApplication.debugTag, CLASS_CreateNotificationsOverview + ": Else Zweig von MenuItem Listener");
            return false;
        }
    }

    /**
     * Zeigt ein Custom Popup Menü an
     *
     * @param view View Das View Objekt, an dem dieses Menü angehängt wird
     */
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this.getApplicationContext(), view);
        popup.inflate(R.menu.not_popupmenu);
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    /**
     * Listener für Button Channel1
     */
    public void onChannel1ButtonCLicked(View view) {
        showPopupMenu(view);
        button1Clicked = true;
    }

    /**
     * Listener für Button Channel2
     */
    public void onChannel2ButtonClicked(View view) {
        showPopupMenu(view);
        button1Clicked = false;
    }
}
