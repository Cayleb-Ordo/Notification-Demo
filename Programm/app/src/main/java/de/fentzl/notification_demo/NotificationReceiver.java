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
    private final String CLASS_NOTIFICATIONRECEIVER = "de.fentzl.notification_demo.NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationController notificationController = NotificationDemoApplication.getAPPLICATION().getNotController();
        /*
        /*Diese Methode brauch man nur wenn man nicht den RequestCode des PendingIntent variiert, sondern den darunterliegenden normalen Intent.
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag, "IF-Statement Class NotificationReceiver, function onReceive \n No specific Action specified");
        } else
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));
         */
        switch (intent.getAction()){
            case NotificationBuilder.ACTION_DISMISS:
                notificationController.dismissNotification(intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, 0));
                break;
            case NotificationBuilder.ACTION_MUTE:
                notificationController.updateMediaCont(intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0), intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
                break;
            case NotificationBuilder.ACTION_REPLY:
                Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
                if (remoteInput != null){
                    CharSequence rplyText = remoteInput.getCharSequence(NotificationBuilder.KEY_TEXTRPLY);
                    NotificationDemoApplication.getAPPLICATION().getMESSAGES().add(new Message(rplyText, null));
                    if (intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0) == NotificationController.notCh1)
                        notificationController.handleReply(intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0), intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
                    else if (intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0) == NotificationController.notCh2)
                        notificationController.handleReply(intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0), intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
                    else
                        Toast.makeText(context, context.getString(R.string.ToIntErr) + ": Kein valider Kanal empfangen!", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Log.e(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONRECEIVER + ": No specific Action received!");
                break;
        }
   }
}
