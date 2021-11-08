package de.fentzl.notification_demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Kontrollklasse die die Notificationen verwaltet
 *
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
    private static final String CHANNEL1_ID = "Channel1";
    private static final String CHANNEL2_ID = "Channel2";
    private final String CLASS_NOTIFICATIONCONTROLLER = "de.fentzl.notification_demo.NotificationController";
    private final int progressMax = 100;
    private final int contentRqC = 1;
    private int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private Context context;
    private NotificationCompat.Builder notbuilder;
    private NotificationManagerCompat notificationManager;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
     *
     * @param context Aplikations-kontext
     */
    public NotificationController(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
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
     *
     * @param selected
     */
    public void notifyChannel1(CreateNotificationsOverview.NotificationType selected) {
        switch (selected){
            case Default:
                Log.d(MainActivity.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Case Default");
                buildDefaultNot(notCh1, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1);
                break;
            case Progress:
                buildProgressbarNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1);
                break;
            case Expandable:
                buildExpandableNot(notCh1, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1);
                break;
            case Media:
                break;
            case Reply:
                break;
            case Custom:
                break;
            default:
                Log.d(MainActivity.debugTag, "Das sollte nicht passieren");
                break;
        }
    }

    /**
     * Setzt eine Notification auf Kanal 1
     */
    public void notifyChannel2(CreateNotificationsOverview.NotificationType selected) {
        switch (selected){
            case Default:
                buildDefaultNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2);
                break;
            case Progress:
                buildProgressbarNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2);
                break;
            case Expandable:
                buildExpandableNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2);
                break;
            case Media:
                break;
            case Reply:
                break;
            case Custom:
                break;
            default:
                Log.d(MainActivity.debugTag, "Das sollte nicht passieren");
                break;
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
     * Erstellt eine Default Nachricht mit übergebbaren Kanaleinstellungen
     *
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     */
    private void buildDefaultNot(int notID, String channelid, String contentTitle, int icon) {
        Intent contentIntent = new Intent(context, CallActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class);
        /*Sollte man nicht den PendingIntent variiern, zb bei unterschiedlicher RequestID, kann man den normalen Intent unterscheidbar machen. Zb eine Eindeutige Action zuweisen.
        dismissIntent.setAction(ACTION_DISMISS + notCH1).putExtra(PAYLOAD,notCh1);
         */
        dismissIntent.setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent pendingcontentInt = PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notbuilder = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotDefContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
        notificationManager.notify(notID, notbuilder.build());
    }

    /**
     * Erstellt eine Progressbar-Notification und startet einen Thread zur Aktualisierung der Notification
     *
     * @param notID     Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon      Integer Id des Icons
     */
    private void buildProgressbarNot(int notID, String channelid, int icon) {
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notbuilder = new NotificationCompat.Builder(context, channelid).setSmallIcon(icon)
                .setContentTitle(context.getString(R.string.NotProgTitle))
                .setContentText(context.getString(R.string.NotProgIndit))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, false);
        notificationManager.notify(notID, notbuilder.build());
        //Thread zur aktualisierung der Notification, damit ein Download simuliert wird
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Hier wird die Progressbar auf unbestimmt gesetzt, heist es läuft ein band anstelle des Fortschrits
                notbuilder.setProgress(0,0,true);
                notificationManager.notify(notID, notbuilder.build());
                SystemClock.sleep(3000); // Damit die Anfangs-Notification kurz stehen bleibt
                //For loop zum aktualisieren der Notification(Fake-Download)
                for (int progress = 0; progress <= progressMax; progress += 10) {
                    notbuilder.setProgress(progressMax, progress, false)
                           .setContentText(context.getString(R.string.NotProgStartText));
                    notificationManager.notify(notID, notbuilder.build());
                    SystemClock.sleep(1000);
                }
                notbuilder.setContentText(context.getString(R.string.NotProgEndText))
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent);
                notificationManager.notify(notID, notbuilder.build());
            }
        }).start();
    }

    /**
     *Erstellt eine Vergrößerbare Nachricht mit übergebbaren Kanaleinstellungen
     *
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     */
    private void buildExpandableNot(int notID, String channelid, String contentTitle, int icon){
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.rwu_logo);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notbuilder = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotPicContent))
                .setLargeIcon(logo)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(logo).bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
        notificationManager.notify(notID, notbuilder.build());
    }

    private void buildMediaConNot(){

    }

    private void buildDirRplyMessStNot(){

    }

    private void buildCustomNot(){

    }
    /**
     * Lässt die Notification verschwinden
     */
    public void dismissNotification(int id) {
        Log.d(MainActivity.debugTag, CLASS_NOTIFICATIONCONTROLLER + " ID: " + id);
        if (id == notCh1) {
            notificationManager.cancel(notCh1);
        } else if (id == notCh2) {
            notificationManager.cancel(notCh2);
        } else
            Log.d(MainActivity.debugTag, "Das sollte nicht passieren");
    }
}


