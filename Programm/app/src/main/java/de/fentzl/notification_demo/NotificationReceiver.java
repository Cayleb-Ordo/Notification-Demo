package de.fentzl.notification_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver der Aufgerufen wird wenn ein ActionButton der Notification ausgelöst wird
 * @author Simon Fentzl
 * @version 1
 */
public class NotificationReceiver extends BroadcastReceiver {
    private NotificationController notificationController;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationController = new NotificationController(context);
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag, "IF-Statement Class NotificationReceiver, function onReceive \n No specific Action specified");
        } else
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));
   }
}
