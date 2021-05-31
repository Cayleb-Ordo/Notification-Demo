package de.fentzl.notification_demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Kontrollklasse die die Notificationen verwaltet
 * @author Simon Fentzl
 * @version 1
 */

public class NotificationController {
    public static final String ACTION_DISMISS = "de.rwu.medi_app.DISMISS";
    private static final String CHANNEL1_ID = "Channel1";
    private static final String CHANNEL2_ID = "Channel2";
    public static final String channel1Name = "Kanal 1";
    public static final String channel2Name = "Kanal 2";
    public static final String channel1Description = "Nachrichten-Kanal 1";
    public static final String channel2Description = "Nachrichten-Kanal 2";
    public final int defaultNotID = 1;
    private final int contentRqC = 0;
    private final int dismissRqC = 2;
    private int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
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

    /**
     * Setzt eine Notification auf Kanal 1
     */
    public void notifyChannel1() {
        Intent contentIntent = new Intent(context, CallActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class);
        dismissIntent.setAction(ACTION_DISMISS);
        PendingIntent pendingcontentInt = PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, dismissRqC, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(context, CHANNEL1_ID)
                .setSmallIcon(R.drawable.ic_channel1)
                .setContentTitle(context.getString(R.string.NotTitleCh1))
                .setContentText(context.getString(R.string.NotContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
        notificationManager.notify(defaultNotID, builder.build());
    }

    /**
     * Setzt eine Notification auf Kanal 1
     */
    public void notifyChannel2() {
        Intent contentIntent = new Intent(context, CallActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class);
        dismissIntent.setAction(ACTION_DISMISS);
        PendingIntent pendingcontentInt = PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, dismissRqC, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(context, CHANNEL2_ID)
                .setSmallIcon(R.drawable.ic_channel2)
                .setContentTitle(context.getString(R.string.NotTitleCh2))
                .setContentText(context.getString(R.string.NotContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
        notificationManager.notify(defaultNotID, builder.build());
    }

    /**
     * Lässt die Notification verschwinden
     */
    public void dismissNotification(){
        notificationManager.cancel(defaultNotID);
    }
}

