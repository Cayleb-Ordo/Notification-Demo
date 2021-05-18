package de.fentzl.notification_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    private NotificationController notificationController;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationController = new NotificationController(context);
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            notificationController.dismissNotification();
        }
    }
}
