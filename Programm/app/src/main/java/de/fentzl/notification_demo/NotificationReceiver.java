package de.fentzl.notification_demo;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * BroadcastReceiver der Aufgerufen wird wenn ein ActionButton der Notification ausgelöst wird
 * @author Simon Fentzl
 * @version 1
 */
public class NotificationReceiver extends BroadcastReceiver {
    private final String NOTIFICATIONRECEIVER = "de.fentzl.notification_demo.NotificationReceiver";
    private NotificationController notificationController;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)) {
            notificationController = new NotificationController(context);
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD, 0));
        /*Diese Methode brauch man nur wenn man nicht den RequestCode des PendingIntent variiert, sondern den darunterliegenden normalen Intent.
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag, "IF-Statement Class NotificationReceiver, function onReceive \n No specific Action specified");
        } else
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));*/
        }else
            Log.d(MainActivity.debugTag, NOTIFICATIONRECEIVER + " No specific Action received!");
   }
}
