package de.fentzl.notification_demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Kontrollklasse die die Notificationen verwaltet
 * @author Simon Fentzl
 * @version 1
 */

public class NotificationController {
    public static final String ACTION_DISMISS = "de.fentzl.notification_demo.DISMISS";
    public static final String ACTION_REPLY = "de.fentzl.notification_demo.REPLY";
    public static final String KEY_TEXTRPLY = "reply";
    public static final String channel1Name = "Kanal 1";
    public static final String channel2Name = "Kanal 2";
    public static final String channel1Description = "Nachrichten-Kanal 1";
    public static final String channel2Description = "Nachrichten-Kanal 2";
    public static final String PAYLOAD = "payload";
    public static final int notCh1 = 1;
    public static final int notCh2 = 2;
    private final String CLASS_NOTIFICATIONCONTROLLER = "de.fentzl.notification_demo.NotificationController";
    private static final String CHANNEL1_ID = "Channel1";
    private static final String CHANNEL2_ID = "Channel2";
    private final int contentRqC = 1;
    private int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
     *
     * @param context Aplikations-kontext
     */
    public NotificationController(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        ;
    }

    /**
     * Erstellt den Notwendigen Kanal für Geräte über Android8.0
     */
    public void createNotificationChannel(boolean createOption) {
        if (createOption) {
            //der if prüft ob wir android 8.0 oder höher haben, sonnst muss er kein channel erstellen.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, channel1Name, importance);
                NotificationChannel channel2 = new NotificationChannel(CHANNEL2_ID, channel2Name, importance);
                channel1.setDescription(channel1Description);
                channel2.setDescription(channel2Description);
                //Channel dem System mitteilen. Danach nicht mehr veränderbar
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel1);
                notificationManager.createNotificationChannel(channel2);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL1_ID, channel1Name, importance);
            channel.setDescription(channel1Description);
            //Channel dem System mitteilen. Danach nicht mehr veränderbar
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /*
    private NotificationCompat.Action buildRplyAction(int reqCode){
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXTRPLY)
                .setLabel(context.getString(R.string.NotRplyLabel))
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent replyIntent = new Intent(context, Re.class);
            PendingIntent replyPendingIntent = PendingIntent.getBroadcast(context,
                    reqCode, replyIntent, PendingIntent.FLAG_ONE_SHOT);
        } else {
            //Activity normal starten
            //und die Notification canceln
        }
        PendingIntent rplyIntent = PendingIntent.getBroadcast(context, reqCode, new Intent(context, MainActivity.class), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Action rplyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                R.string.NotActionReply,
                rplyIntent).build();
    }*/

    /**
     * Setzt eine Notification auf Kanal 1
     */
    private void buildDefaultNot(int channel){
        Intent contentIntent = new Intent(context, CallActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class);
        /*Sollte man nicht den PendingIntent variiern, zb bei unterschiedlicher RequestID, kann man den normalen Intent unterscheidbar machen. Zb eine Eindeutige Action zuweisen.
        dismissIntent.setAction(ACTION_DISMISS + notCH1).putExtra(PAYLOAD,notCh1);
         */
        dismissIntent.setAction(ACTION_DISMISS).putExtra(PAYLOAD,channel);
        PendingIntent pendingcontentInt = PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, channel, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(context, CHANNEL1_ID)
                .setSmallIcon(R.drawable.ic_channel1)
                .setContentTitle(context.getString(R.string.NotTitleCh1))
                .setContentText(context.getString(R.string.NotContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
        notificationManager.notify(channel, builder.build());
    }

    public void notifyChannel1() {
        buildDefaultNot(notCh1);
    }

    /**
     * Setzt eine Notification auf Kanal 1
     */
    public void notifyChannel2() {
        buildDefaultNot(notCh2);
    }

    private void buildProgressbarNot(){

    }
    /**
     * Lässt die Notification verschwinden
     */
    public void dismissNotification(int id) {
        Log.d(MainActivity.debugTag, CLASS_NOTIFICATIONCONTROLLER + " ID: " + Integer.toString(id));
        if(id == notCh1) {
            notificationManager.cancel(notCh1);
        }else if (id == notCh2){
        notificationManager.cancel(notCh2);
    }else
        Log.d(MainActivity.debugTag,"Das sollte nicht passieren");
    }
}


