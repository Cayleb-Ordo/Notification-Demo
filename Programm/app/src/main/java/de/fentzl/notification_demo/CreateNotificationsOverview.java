package de.fentzl.notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

/**
 * Diese Klasse setzt je nach Benuztereingabe eine Bestimmte Notification
 * @author Simon Fentzl
 * @version 1
 */
public class CreateNotificationsOverview extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private NotificationController notificationController;

    enum NotificationType {
        Default,
        Progress,
        Expandable,
        Custom,
        Media,
        Reply
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationController = new NotificationController(this.getApplicationContext());
        setContentView(R.layout.activity_create_not_overv);
        /*
        Toolbar-Einstellungen
         */
        Toolbar toolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent tmpInt = new Intent(CreateNotificationsOverview.this, MainActivity.class);
        switch (item.getItemId()) {
            case R.id.Pop_Default:
                notificationController.notifyChannel1(NotificationType.Default);
                startActivity(tmpInt);
                return true;
            case R.id.Pop_Progress:
                notificationController.notifyChannel1(NotificationType.Progress);
                startActivity(tmpInt);
                return true;
            case R.id.Pop_Expandable:
                notificationController.notifyChannel1(NotificationType.Expandable);
                startActivity(tmpInt);
                return true;
            case R.id.Pop_Media:
                notificationController.notifyChannel1(NotificationType.Media);
                startActivity(tmpInt);
                return true;
            case R.id.Pop_Rply:
                notificationController.notifyChannel1(NotificationType.Reply);
                startActivity(tmpInt);
                return true;
            case R.id.Pop_Custom:
                notificationController.notifyChannel1(NotificationType.Custom);
                startActivity(tmpInt);
                return true;
            default:
                Log.d(MainActivity.debugTag, "Klappt nicht ");
                return false;
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this.getApplicationContext(), view);
        popup.inflate(R.menu.not_popupmenu);
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    /**
     *
     */
    public void onChannel1ButtonCLicked(View view) {
        showPopupMenu(view);
    }

    /**
     *
     */
    public void onChannel2ButtonClicked(View view) {
        showPopupMenu(view);
    }
}
