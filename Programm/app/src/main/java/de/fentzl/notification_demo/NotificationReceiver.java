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

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

/**
 * BroadcastReceiver der aufgerufen wird, wenn ein ActionButton der Notification ausgelöst wird
 * @author Simon Fentzl
 * @version 2
 */
public class NotificationReceiver extends BroadcastReceiver {
    private final String CLASS_NOTIFICATIONRECEIVER = "de.fentzl.notification_demo.NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationController notificationController = NotificationDemoApplication.getAPPLICATION().getNotController();
        /*
         * Diese Methode brauch man nur wenn man nicht den RequestCode des PendingIntent variiert, sondern den darunterliegenden normalen Intent.
        if(intent.getAction().equals(NotificationController.ACTION_DISMISS)){
            Log.d(MainActivity.debugTag, "IF-Statement Class NotificationReceiver, function onReceive \n No specific Action specified");
        } else
            notificationController.dismissNotification(intent.getIntExtra(NotificationController.PAYLOAD,0));
         */
        switch (Objects.requireNonNull(intent.getAction())){
            case NotificationBuilder.ACTION_DISMISS:
                notificationController.dismissNotification(intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
                break;
            case NotificationBuilder.ACTION_MUTE:
                notificationController.mediaConNotMute(intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0), intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
                break;
            case NotificationBuilder.ACTION_PAUSE:
                notificationController.mediaConNotPause(intent.getIntExtra(NotificationBuilder.PAYLOADCHID, 0), intent.getIntExtra(NotificationBuilder.PAYLOADNOTID, -1));
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
