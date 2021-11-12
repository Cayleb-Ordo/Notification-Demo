package de.fentzl.notification_demo;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        notificationController = new NotificationController(context);/*
        /*Diese Methode brauch man nur wenn man nicht den RequestCode des PendingIntent variiert, sondern den darunterliegenden normalen Intent.
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag, "IF-Statement Class NotificationReceiver, function onReceive \n No specific Action specified");
        } else
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));*/
        switch (intent.getAction()){
            case NotificationController.ACTION_DISMISS:
                notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD, 0));
                Log.d(MainActivity.debugTag, NOTIFICATIONRECEIVER + "case Action Dismiss");
                break;
            /*case NotificationController.ACTION_MUTE:
                notificationController.updateMediaCont(intent.getIntExtra(NotificationController.PAYLOAD, 0));
                Log.d(MainActivity.debugTag, NOTIFICATIONRECEIVER + " case Action Mute");
                break;*/
            case NotificationController.ACTION_REPLY:
                Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
                if (remoteInput != null){
                    CharSequence rplyText = remoteInput.getCharSequence(NotificationController.KEY_TEXTRPLY);
                    MainActivity.MESSAGES.add(new Message(rplyText, null));
                    if (intent.getIntExtra(NotificationController.PAYLOAD, 0) == NotificationController.notCh1)
                        notificationController.notifyChannel1(CreateNotificationsOverview.NotificationType.Reply);
                    else if (intent.getIntExtra(NotificationController.PAYLOAD, 0) == NotificationController.notCh2)
                        notificationController.notifyChannel2(CreateNotificationsOverview.NotificationType.Reply);
                    else
                        Toast.makeText(context, context.getString(R.string.ToIntErr), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Log.d(MainActivity.debugTag, NOTIFICATIONRECEIVER + " No specific Action received!");
                break;
        }
   }
}
