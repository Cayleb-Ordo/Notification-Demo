package de.fentzl.notification_demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

/**
 * Kontrollklasse die die Notificationen verwaltet
 * @author Simon Fentzl
 * @version 1
 */

public class NotificationController {
    public static final String channel1Name = "Kanal 1";
    public static final String channel2Name = "Kanal 2";
    public static final String channel1Description = "Nachrichten-Kanal 1";
    public static final String channel2Description = "Nachrichten-Kanal 2";
    public static final int notCh1 = 1;
    public static final int notCh2 = 2;
    public static final int progressMax = 100;
    private static final String CHANNEL1_ID = "Channel1";
    private static final String CHANNEL2_ID = "Channel2";
    private final String CLASS_NOTIFICATIONCONTROLLER = "de.fentzl.notification_demo.NotificationController";
    private final int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private final Context context;
    private final NotificationManagerCompat notificationManager;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
     * @param context Aplikations-kontext
     */
    public NotificationController(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    /**
     * Erstellt den Notwendigen Kanal für Geräte über Android8.0
     * @param createOption Boolean Gibt an ob zwei oder nur ein Notification Channel erstellt werden soll.
     */
    public void createNotificationChannels(boolean createOption) {
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
     * @param selected Enum Gibt an was der Benutzer ausgewählt hat.
     */
    public void notifyChannel1(CreateNotificationsOverview.NotificationType selected) {
        switch (selected) {
            case Default:
                Log.d(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Case Default");
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildDefaultNot(notCh1, CHANNEL1_ID,context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.notBuilder.buildProgressbarNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1);
                notificationManager.notify(notCh1,tmpBuilder.build() );
                creatThread(tmpBuilder, notCh1);
                break;
            case Expandable:
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildExpandableNot(notCh1, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case BigText:
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildBigTextStyleNot(notCh1, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Media:
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildMediaConNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            case Reply:
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildDirRplyMessStNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            case Custom:
                notificationManager.notify(notCh1,
                        NotificationDemoApplication.notBuilder.buildCustomNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            default:
                Log.d(NotificationDemoApplication.debugTag, "Das sollte nicht passieren");
                break;
        }
    }

    /**
     * Setzt eine Notification auf Kanal 2
     * @param selected Enum Gibt an was der Benutzer ausgewählt hat.
     */
    public void notifyChannel2(CreateNotificationsOverview.NotificationType selected) {
        switch (selected) {
            case Default:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildDefaultNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.notBuilder.buildProgressbarNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2);
                notificationManager.notify(notCh2, tmpBuilder.build());
                creatThread(tmpBuilder, notCh2);
                break;
            case Expandable:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildExpandableNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case BigText:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildBigTextStyleNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Media:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildMediaConNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            case Reply:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildDirRplyMessStNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            case Custom:
                notificationManager.notify(notCh2,
                        NotificationDemoApplication.notBuilder.buildCustomNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            default:
                Log.d(NotificationDemoApplication.debugTag, "Das sollte nicht passieren");
                break;
        }
    }

    private void creatThread(NotificationCompat.Builder notBuilder, int notID){
        //Thread zur aktualisierung der Notification, damit ein Download simuliert wird
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Hier wird die Progressbar auf unbestimmt gesetzt, heist es läuft ein band anstelle des Fortschrits
                notBuilder.setProgress(0, 0, true).setOnlyAlertOnce(true);
                notificationManager.notify(notID, notBuilder.build());
                SystemClock.sleep(3000); // Damit die Anfangs-Notification kurz stehen bleibt
                //For loop zum aktualisieren der Notification(Fake-Download)
                for (int progress = 0; progress <= progressMax; progress += 10) {
                    notBuilder.setProgress(progressMax, progress, false)
                            .setContentText(context.getString(R.string.NotProgStartText)).setOnlyAlertOnce(true);
                    notificationManager.notify(notID, notBuilder.build());
                    SystemClock.sleep(1000);
                }
                notBuilder.setContentText(context.getString(R.string.NotProgEndText))
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .setContentIntent(NotificationDemoApplication.notBuilder.buildContentIntent())
                        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), NotificationDemoApplication.notBuilder.buildDismissIntent(notID));
                notificationManager.notify(notID, notBuilder.build());
            }
        }).start();
    }

    /*public void updateMediaCont(int notID) {
        NotificationDemoApplication.notBuilder.updateBuilder = new NotificationCompat.Builder(context, mediaConNot).addAction(R.drawable.ic_volume_off, context.getString(R.string.NotExpAMute), null);
        notificationManager.notify(notID, updateBuilder.build());
    }*/

    /**
     * Lässt die Notification verschwinden
     */
    public void dismissNotification(int id) {
        //Log.d(MainActivity.debugTag, CLASS_NOTIFICATIONCONTROLLER + " ID: " + id);
        if (id == notCh1) {
            notificationManager.cancel(notCh1);
        } else if (id == notCh2) {
            notificationManager.cancel(notCh2);
        } else
            Log.d(NotificationDemoApplication.debugTag, "Das sollte nicht passieren");
    }
}