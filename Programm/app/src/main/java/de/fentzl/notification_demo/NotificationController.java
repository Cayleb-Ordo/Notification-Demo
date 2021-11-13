package de.fentzl.notification_demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

/**
 * Kontrollklasse die die Notificationen verwaltet
 *
 * @author Simon Fentzl
 * @version 1
 */

public class NotificationController {
    public static final String ACTION_DISMISS = "de.fentzl.notification_demo.DISMISS";
    public static final String ACTION_REPLY = "de.fentzl.notification_demo.REPLY";
    public static final String ACTION_MUTE = "de.fentzl.notificatin_demo.MUTE";
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
    private final String personKey = "de.fentzl.me";
    private final int progressMax = 100;
    private final int contentRqC = 1;
    private int importance = NotificationManager.IMPORTANCE_DEFAULT;
    private Context context;
    private NotificationCompat.Builder updateBuilder;
    private NotificationManagerCompat notificationManager;
    private Notification mediaConNot;
    private Person me;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
     *
     * @param context Aplikations-kontext
     */
    public NotificationController(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        this.me = new Person.Builder().setName(context.getString(R.string.MessageMe)).setKey(personKey).build();
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
     * @param selected
     */
    public void notifyChannel1(CreateNotificationsOverview.NotificationType selected) {
        switch (selected) {
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
            case BigText:
                buildBigTextStyleNot(notCh1, CHANNEL1_ID, context.getString(R.string.NotTitleCh1), R.drawable.ic_channel1);
                break;
            case Media:
                buildMediaConNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1);
                break;
            case Reply:
                buildDirRplyMessStNot(notCh1, CHANNEL1_ID, R.drawable.ic_channel1);
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
        switch (selected) {
            case Default:
                buildDefaultNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2);
                break;
            case Progress:
                buildProgressbarNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2);
                break;
            case Expandable:
                buildExpandableNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2);
                break;
            case BigText:
                buildBigTextStyleNot(notCh2, CHANNEL2_ID, context.getString(R.string.NotTitleCh2), R.drawable.ic_channel2);
                break;
            case Media:
                buildMediaConNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2);
                break;
            case Reply:
                buildDirRplyMessStNot(notCh2, CHANNEL2_ID, R.drawable.ic_channel2);
                break;
            case Custom:
                break;
            default:
                Log.d(MainActivity.debugTag, "Das sollte nicht passieren");
                break;
        }
    }

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
        Notification defaultNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotDefContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingcontentInt)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true) // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
                .build();
        notificationManager.notify(notID, defaultNot);
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
        NotificationCompat.Builder progressNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(context.getString(R.string.NotProgTitle))
                .setContentText(context.getString(R.string.NotProgIndit))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setProgress(progressMax, 0, false);
        notificationManager.notify(notID, progressNot.build());
        //Thread zur aktualisierung der Notification, damit ein Download simuliert wird
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Hier wird die Progressbar auf unbestimmt gesetzt, heist es läuft ein band anstelle des Fortschrits
                progressNot.setProgress(0, 0, true).setOnlyAlertOnce(true);
                notificationManager.notify(notID, progressNot.build());
                SystemClock.sleep(3000); // Damit die Anfangs-Notification kurz stehen bleibt
                //For loop zum aktualisieren der Notification(Fake-Download)
                for (int progress = 0; progress <= progressMax; progress += 10) {
                    progressNot.setProgress(progressMax, progress, false)
                            .setContentText(context.getString(R.string.NotProgStartText)).setOnlyAlertOnce(true);
                    notificationManager.notify(notID, progressNot.build());
                    SystemClock.sleep(1000);
                }
                progressNot.setContentText(context.getString(R.string.NotProgEndText))
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent);
                notificationManager.notify(notID, progressNot.build());
            }
        }).start();
    }

    /**
     * Erstellt eine Vergrößerbare Nachricht mit übergebbaren Kanaleinstellungen
     *
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     */
    private void buildExpandableNot(int notID, String channelid, String contentTitle, int icon) {
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.rwu_logo);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification expandNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotPicContent))
                .setLargeIcon(logo)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(logo).bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(notID,expandNot);
    }

    private void buildMediaConNot(int notID, String channelid, int icon) {
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mausi);
        Intent muteIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_MUTE).putExtra(PAYLOAD, notID);
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent mutePendingIntent = PendingIntent.getBroadcast(context, notID, muteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mediaConNot= new NotificationCompat.Builder(context, channelid)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_volume_on, context.getString(R.string.NotExpAMute), mutePendingIntent)
                .addAction(R.drawable.ic_prev, context.getString(R.string.NotExpAPrev), null)
                .addAction(R.drawable.ic_pause, context.getString(R.string.NotExpAPause), null)
                .addAction(R.drawable.ic_next, context.getString(R.string.NotExpANext), null)
                .addAction(R.drawable.ic_close, context.getString(R.string.NotExpAAbort), dismissPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)) //diese Integer beziehen sich auf die Reihenfolge der Action Buttons
                .setContentTitle(context.getString(R.string.NotExpanTitle))
                .setContentText(context.getString(R.string.NotExpanText))
                .setLargeIcon(logo)
                .setSmallIcon(icon).build();
        notificationManager.notify(notID, mediaConNot);
    }

    private void buildBigTextStyleNot(int notID, String channelid, String contentTitle, int icon) {
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification bigTxtNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotBTextContent))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.NotBText_lorem))
                        .setBigContentTitle(context.getString(R.string.NotBTextConTitle))
                        .setSummaryText(context.getString(R.string.NotBTextSumm)))
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(notID, bigTxtNot);
    }

    private void buildDirRplyMessStNot(int notID,String channelid, int icon) {
        Intent dismissIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //RemoteInput, anhand dessen wird er eingegebene Text später entnommen
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXTRPLY)
                .setLabel(context.getString(R.string.NotRplyLabel))
                .build();
        //Antwort ActionButton, dem der RemotInput angehängt wird.
        Intent rplyIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_REPLY).putExtra(PAYLOAD,notID);
        PendingIntent rplyPendingIntent = PendingIntent.getBroadcast(context, notID, rplyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action rplyAction = new NotificationCompat.Action.Builder(R.drawable.ic_reply, context.getString(R.string.NotActionReply), rplyPendingIntent )
                .addRemoteInput(remoteInput)
                .build();
        //MessangingStyle, wichtig hier das Person Objekt. Nur eine Charsequence ist in der alten Funktion.
        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(me)
                .setConversationTitle("Gruppen Chat");
        for (Message chatMessage : MainActivity.MESSAGES){
            NotificationCompat.MessagingStyle.Message notMessage = new NotificationCompat.MessagingStyle.Message(
                    chatMessage.getText(),
                    chatMessage.getTimestamp(),
                    chatMessage.getSender()
            );
            messagingStyle.addMessage(notMessage);
        }
        //Eigentlich Notification bauen
        Notification rplyNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setStyle(messagingStyle)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
                .addAction(rplyAction)
                .setColor(Color.GREEN)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        notificationManager.notify(notID, rplyNot);
    }

    private void buildCustomNot() {

    }

    public void updateMediaCont(int notID) {
        updateBuilder = new NotificationCompat.Builder(context, mediaConNot).addAction(R.drawable.ic_volume_off, context.getString(R.string.NotExpAMute), null);
        notificationManager.notify(notID, updateBuilder.build());
    }

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
            Log.d(MainActivity.debugTag, "Das sollte nicht passieren");
    }
}


