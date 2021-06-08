package de.fentzl.notification_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    private NotificationController notificationController;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationController = new NotificationController(context);
        Log.d(MainActivity.debugTag,"Außerhalb des IF");
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag,"Innerhalb des IF");
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));
        }
    }
}
