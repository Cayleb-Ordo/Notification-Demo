/*
 *     Copyright (C) 2021-2023 Simon Fentzl
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

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Kontrollklasse, die die Notificationen verwaltet
 * @author Simon Fentzl
 * @version 3
 */

public class NotificationController {
    public static final int notCh1 = 1;
    public static final int notCh2 = 2;
    public static final int progressMax = 100;
    private static final String CHANNEL1_ID = "Channel1";
    private static final String CHANNEL2_ID = "Channel2";
    private final String CLASS_NOTIFICATIONCONTROLLER = "de.fentzl.notification_demo.NotificationController";
    private final Context context;
    private final NotificationManagerCompat notificationManager;
    private boolean muteToggle = false;
    private boolean pauseToggle = false;

    /**
     * Konstruktor, damit der Notification Manager mit Kontext initialisiert werden kann
     * @param context Aplikations-kontext
     */
    public NotificationController(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    /**
     * Erstellt den Notwendigen Kanal für Geräte über Android8.0
     * @param createOption Gibt an ob zwei oder nur ein Notification Channel erstellt werden soll.
     */
    public void createNotificationChannels(boolean createOption) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (createOption) {
            //der if prüft ob wir android 8.0 oder höher haben, sonnst muss er keinen Kanal erstellen.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, context.getString(R.string.Chan1Name), importance);
                NotificationChannel channel2 = new NotificationChannel(CHANNEL2_ID, context.getString(R.string.Chan2Name), importance);
                channel1.setDescription(context.getString(R.string.Chan1Info));
                channel2.setDescription(context.getString(R.string.Chan2Info));
                //Kanal dem System mitteilen. Danach nicht mehr veränderbar
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel1);
                notificationManager.createNotificationChannel(channel2);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL1_ID, context.getString(R.string.ChanName), importance);
            channel.setDescription(context.getString(R.string.ChanInfo));
            //Kanal dem System mitteilen. Danach nicht mehr veränderbar
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Setzt eine Notification auf Kanal 1
     * @param selected Gibt an was der Benutzer ausgewählt hat.
     */
    public void notifyChannel1(CreateNotificationsOverview.NotificationType selected) {
        if (!notificationManager.areNotificationsEnabled()){
            Log.i(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Notifications nicht aktiviert!");
            Toast.makeText(context, R.string.NotDisabled, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (selected) {
            case Default:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDefaultNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL1_ID,context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildProgressbarNot(CHANNEL1_ID, R.drawable.ic_channel1);
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),tmpBuilder.build());
                createThread(tmpBuilder, NotificationDemoApplication.getAPPLICATION().getIdCounter());
                break;
            case BigPicture:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildBigPictureStyleNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case BigText:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildBigTextStyleNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1));
                break;
            case Media:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), notCh1, CHANNEL1_ID, R.drawable.ic_channel1, false, false));
                break;
            case Reply:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDirRplyMessStNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), notCh1, CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            case Custom:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildCustomNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL1_ID, R.drawable.ic_channel1));
                break;
            default:
                Log.e(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Das sollte nicht passieren");
                break;
        }
        NotificationDemoApplication.getAPPLICATION().setIdCounter(NotificationDemoApplication.getAPPLICATION().getIdCounter() + 1);
    }

    /**
     * Setzt eine Notification auf Kanal 2
     * @param selected Gibt an was der Benutzer ausgewählt hat.
     */
    public void notifyChannel2(CreateNotificationsOverview.NotificationType selected) {
        if (!notificationManager.areNotificationsEnabled()){
            Log.i(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Notifications nicht aktiviert!");
            Toast.makeText(context, R.string.NotDisabled, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (selected) {
            case Default:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDefaultNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Progress:
                NotificationCompat.Builder tmpBuilder = NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildProgressbarNot(CHANNEL2_ID, R.drawable.ic_channel2);
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(), tmpBuilder.build());
                createThread(tmpBuilder, NotificationDemoApplication.getAPPLICATION().getIdCounter());
                break;
            case BigPicture:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildBigPictureStyleNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case BigText:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildBigTextStyleNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2));
                break;
            case Media:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), notCh2, CHANNEL2_ID, R.drawable.ic_channel2, false, false));
                break;
            case Reply:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDirRplyMessStNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), notCh2, CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            case Custom:
                notificationManager.notify(NotificationDemoApplication.getAPPLICATION().getIdCounter(),
                        NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildCustomNot(NotificationDemoApplication.getAPPLICATION().getIdCounter(), CHANNEL2_ID, R.drawable.ic_channel2));
                break;
            default:
                Log.e(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Das sollte nicht passieren");
                break;
        }
        NotificationDemoApplication.getAPPLICATION().setIdCounter(NotificationDemoApplication.getAPPLICATION().getIdCounter() + 1);
    }

    /**
     * Erstellt den Hintergrundthread für die Progressbar Notification
     * @param notBuilder Builder-Objekt zum aktualisieren der Nachricht
     * @param notID ID der Notification
     */
    private void createThread(NotificationCompat.Builder notBuilder, int notID){
        //Thread zur Aktualisierung der Notification, damit ein Download simuliert wird
        new Thread(() -> {
            //Hier wird die Progressbar auf unbestimmt gesetzt, heist es läuft ein Band anstelle des Fortschritsbalkens
            notBuilder.setProgress(0, 0, true).setOnlyAlertOnce(true);
            notificationManager.notify(notID, notBuilder.build());
            SystemClock.sleep(3000); // Damit die Anfangs-Notification kurz stehen bleibt und der ubestimmte Balken sichtbar ist
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
                    .setContentIntent(NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildContentIntent())
                    .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDismissIntent(notID));
            notificationManager.notify(notID, notBuilder.build());
        }).start();
    }

    /**
     * Verändert das Icon für den Ton der Mediakontrollen Benachrichtigung
     * @param channelID ID des Kanals
     * @param notID ID der Notification
     */
    public void mediaConNotMute(int channelID, int notID) {
        Log.i(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": mediaConNotMute");
        muteToggle =! muteToggle;
        if(channelID == notCh1)
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(notID, channelID, CHANNEL1_ID, R.drawable.ic_channel1, muteToggle, pauseToggle));
        else if (channelID == notCh2)
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(notID, channelID, CHANNEL2_ID, R.drawable.ic_channel2, muteToggle,pauseToggle));
    }

    /**
     * Verändert das Icon für pausieren der Mediakontrollen Benachrichtigung
     * @param channelID ID des Kanals
     * @param notID ID der Notification
     */
    public void mediaConNotPause(int channelID, int notID){
        Log.i(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": medieConNotPause");
        pauseToggle =! pauseToggle;
        if(channelID == notCh1)
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(notID, channelID, CHANNEL1_ID, R.drawable.ic_channel1, muteToggle, pauseToggle));
        else if (channelID == notCh2)
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildMediaConNot(notID, channelID, CHANNEL2_ID, R.drawable.ic_channel2, muteToggle,pauseToggle));
    }

    /**
     * Aktualisiert die übergebene MessagingStyle Notification
     * @param channelID Notification-Kanal Identifizierung
     * @param notID Notification ID
     */
    public void handleReply(int channelID, int notID){
        if(channelID == notCh1) {
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDirRplyMessStNot(notID, channelID, CHANNEL1_ID, R.drawable.ic_channel2));
        } else if (channelID == notCh2){
            notificationManager.notify(notID, NotificationDemoApplication.getAPPLICATION().getNotBuilder().buildDirRplyMessStNot(notID, channelID, CHANNEL2_ID, R.drawable.ic_channel2));
        } else {
            Log.e(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Konnte den Kanal nicht identifizieren!");
        }
    }

    /**
     * Lässt die Notification verschwinden
     * @param id ID der Notification
     */
    public void dismissNotification(int id) {
        //Log.d(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + " ID: " + id);
        if (id < 0)
            Log.e(NotificationDemoApplication.debugTag, CLASS_NOTIFICATIONCONTROLLER + ": Das sollte nicht passieren");
        else
            notificationManager.cancel(id);
    }
}