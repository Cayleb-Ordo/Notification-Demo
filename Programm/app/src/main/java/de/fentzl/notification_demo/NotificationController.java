package de.fentzl.notification_demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

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
    private NotificationCompat.Builder updateBuilder;
    private Random random = new Random();
    private int randMax = 20;
    private int randMin = 1;

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
        int stray = random.nextInt(randMax -randMin) + randMin;
        switch (selected) {
            case Default:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildDefaultNot(stray, CHANNEL1_ID,context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.notBuilder.buildProgressbarNot(stray, CHANNEL1_ID, R.drawable.ic_channel1);
                notificationManager.notify(stray,tmpBuilder.build() );
                createThread(tmpBuilder, stray);
                break;
            case BigPicture:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildBigPictureStyleNot(stray, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case BigText:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildBigTextStyleNot(stray, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Media:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildMediaConNot(stray, CHANNEL1_ID, R.drawable.ic_channel1, false));
                break;
            case Reply:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildDirRplyMessStNot(stray, CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            case Custom:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildCustomNot(stray, CHANNEL1_ID, R.drawable.ic_channel1));
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
        int stray = random.nextInt(randMax -randMin) + randMin;
        switch (selected) {
            case Default:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildDefaultNot(stray, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.notBuilder.buildProgressbarNot(stray, CHANNEL2_ID, R.drawable.ic_channel2);
                notificationManager.notify(stray, tmpBuilder.build());
                createThread(tmpBuilder, stray);
                break;
            case BigPicture:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildBigPictureStyleNot(stray, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case BigText:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildBigTextStyleNot(stray, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Media:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildMediaConNot(stray, CHANNEL2_ID, R.drawable.ic_channel2, false));
                break;
            case Reply:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildDirRplyMessStNot(stray, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            case Custom:
                notificationManager.notify(stray,
                        NotificationDemoApplication.notBuilder.buildCustomNot(stray, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            default:
                Log.d(NotificationDemoApplication.debugTag, "Das sollte nicht passieren");
                break;
        }
    }

    /**
     * Erstellt den Hintergrundthread für die Progressbar Notification
     * @param notBuilder NotificationCompat.Builder builder-Objekt zum aktualisieren der Nachricht
     * @param notID Integer ID der Notification
     */
    private void createThread(NotificationCompat.Builder notBuilder, int notID){
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

    /**
     * Aktualisiert die Mediakontrollen Notification(aktuell nicht verwendet)
     * @param notID Integer ID der Notification
     */
    public void updateMediaCont(int notID) {
        Log.d(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": updateMediaCont");
        if(notID == notCh1)
            notificationManager.notify(notID, NotificationDemoApplication.notBuilder.buildMediaConNot(notID, channel1Name, R.drawable.ic_channel1, true));
        else
            notificationManager.notify(notID, NotificationDemoApplication.notBuilder.buildMediaConNot(notID, channel2Name, R.drawable.ic_channel2, true));
    }

    /**
     * Lässt die Notification verschwinden
     * @param id Integer ID der Notification
     */
    public void dismissNotification(int id) {
        //Log.d(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + " ID: " + id);
        if (id == 0)
            Log.d(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ":Das sollte nicht passieren");
        else
            notificationManager.cancel(id);
    }
}