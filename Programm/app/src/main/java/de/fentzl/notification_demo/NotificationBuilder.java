package de.fentzl.notification_demo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
/**
 * Bau-Klasse für Notifications
 * @author Simon Fentzl
 * @version 1
 */
public class NotificationBuilder {
    public static final String ACTION_DISMISS = "de.fentzl.notification_demo.DISMISS";
    public static final String ACTION_REPLY = "de.fentzl.notification_demo.REPLY";
    public static final String ACTION_MUTE = "de.fentzl.notificatin_demo.MUTE";
    public static final String KEY_TEXTRPLY = "reply";
    public static final String PAYLOADCHID = "payloadCHID";
    public static final String PAYLOADNOTID = "payloadNotID";
    public Notification mediaConNot;
    private final String CLASS_NOTIFICATIONBUILDER = "de.fentzl.notification_demo.NotificationBuilder";
    private final String NOTIFICATION_GROUP_KEY = "de.fentzl.notification_demo.Gruppe";
    private final Person me;
    private final Context context;

    /**
     * Konstruktor, damit der Notification Builder mit context initialisiert werden kann
     * @param context Aplikations-kontext
     */
    public NotificationBuilder(Context context) {
        this.context = context;
        String personKey = "de.fentzl.me";
        this.me = new Person.Builder().setName(context.getString(R.string.MessageMe)).setKey(personKey).build();
    }

    /**
     * Erstellt den Content Intent
     * @return PendingIntent Gibt den ContentIntent zurück
     */
    public PendingIntent buildContentIntent(){
        Intent contentIntent = new Intent(context, CallActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int contentRqC = 1;
        return PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT );
    }

    /**
     * Erstellt den Dismiss Intent
     * @param notID Integer ID der Notification
     * @return PendingIntent Gibt den DismissIntent zurück
     */
    public PendingIntent buildDismissIntent(int notID){
        /*Sollte man nicht den PendingIntent variiern, zb bei unterschiedlicher RequestID, kann man den normalen Intent unterscheidbar machen. Zb eine Eindeutige Action zuweisen.
        dismissIntent.setAction(ACTION_DISMISS + notCH1).putExtra(PAYLOAD,notCh1);
         */
        Intent dismissIntent = new Intent(context, NotificationReceiver.class)
                .setAction(ACTION_DISMISS).putExtra(PAYLOADNOTID, notID);
        return PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }
    /**
     * Erstellt eine Default Nachricht mit übergebbaren Kanaleinstellungen
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildDefaultNot(int notID, String channelid, String contentTitle, int icon) {
        return new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotDefContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(buildContentIntent())
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setAutoCancel(true) // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }

    /**
     * Erstellt eine Progressbar-Notification und startet einen Thread zur Aktualisierung der Notification
     * @param notID     Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon      Integer Id des Icons
     * @return NotificationCompat.Builder Gibt das NotificationCompat.Builder Objekt zurück
     */
    public NotificationCompat.Builder buildProgressbarNot(int notID, String channelid, int icon) {
        return new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(context.getString(R.string.NotProgTitle))
                .setContentText(context.getString(R.string.NotProgIndit))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(buildContentIntent())
                .setGroup(NOTIFICATION_GROUP_KEY)
                .setProgress(NotificationController.progressMax, 0, false);
    }

    /**
     * Erstellt eine Vergrößerbare Nachricht mit übergebbaren Kanaleinstellungen
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildBigPictureStyleNot(int notID, String channelid, String contentTitle, int icon) {
        return new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotPicContent))
                .setContentIntent(buildContentIntent())
                .setLargeIcon(NotificationDemoApplication.rwu_logo)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(NotificationDemoApplication.rwu_logo).bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setAutoCancel(true)
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }

    /**
     * Erstellt eine Mediakontrollen Notification
     * @param notID Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon Integer Id des Icons
     * @param update Boolean Gibt an, ob die Notification ein Update erhalten soll oder nicht.(Aktuell nicht verwendet)
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildMediaConNot(int notID, String channelid, int icon, boolean update) {
        Intent muteIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_MUTE).putExtra(PAYLOADNOTID, notID);
        PendingIntent mutePendingIntent = PendingIntent.getBroadcast(context, notID, muteIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        mediaConNot = new NotificationCompat.Builder(context, channelid)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_volume_on, context.getString(R.string.NotExpAMute), mutePendingIntent)
                .addAction(R.drawable.ic_prev, context.getString(R.string.NotExpAPrev), null)
                .addAction(R.drawable.ic_pause, context.getString(R.string.NotExpAPause), null)
                .addAction(R.drawable.ic_next, context.getString(R.string.NotExpANext), null)
                .addAction(R.drawable.ic_close, context.getString(R.string.NotExpAAbort), buildDismissIntent(notID))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)) //diese Integer beziehen sich auf die Reihenfolge der Action Buttons
                .setContentTitle(context.getString(R.string.NotExpanTitle))
                .setContentText(context.getString(R.string.NotExpanText))
                .setLargeIcon(NotificationDemoApplication.mausi_logo)
                .setSmallIcon(icon)
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
        return mediaConNot;
    }

    /**
     * Erstellt eine BigText Style Notification
     * @param notID Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon Integer Id des Icons
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildBigTextStyleNot(int notID, String channelid, String contentTitle, int icon) {
        return new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotBTextContent))
                .setContentIntent(buildContentIntent())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.NotBText_lorem))
                        .setBigContentTitle(context.getString(R.string.NotBTextConTitle))
                        .setSummaryText(context.getString(R.string.NotBTextSumm)))
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setAutoCancel(true)
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }

    /**
     * Aktualisiert oder erstellt eine Messagingstyle Notification
     * @param notID Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon Integer Id des Icons
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildDirRplyMessStNot(int notID,String channelid, int icon, int chanID) {
        //RemoteInput, anhand dessen wird der eingegebene Text später entnommen
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXTRPLY)
                .setLabel(context.getString(R.string.NotRplyLabel))
                .build();
        //Antwort ActionButton, dem der RemoteInput angehängt wird.
        Intent rplyIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_REPLY).putExtra(PAYLOADNOTID, notID).putExtra(PAYLOADCHID, chanID  );
        PendingIntent rplyPendingIntent = PendingIntent.getBroadcast(context, notID, rplyIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action rplyAction = new NotificationCompat.Action.Builder(R.drawable.ic_reply, context.getString(R.string.NotActionReply), rplyPendingIntent )
                .addRemoteInput(remoteInput)
                .build();
        //MessangingStyle, wichtig hier das Person Objekt. Nur eine Charsequence ist in der alten Funktion.
        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(me);
        messagingStyle.setConversationTitle(context.getString(R.string.MessageTitle));
        for (Message chatMessage : MainActivity.getMessages()){
            NotificationCompat.MessagingStyle.Message notMessage = new NotificationCompat.MessagingStyle.Message(
                    chatMessage.getText(),
                    chatMessage.getTimestamp(),
                    chatMessage.getSender()
            );
            messagingStyle.addMessage(notMessage); // Fügt die Nachricht dem Style hinzu
        }
        //Eigentliche Notification bauen
        return new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentIntent(buildContentIntent())
                .setStyle(messagingStyle)
                .addAction(rplyAction)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setColor(context.getColor(R.color.Primary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }

    /**
     * Erstellt eine eigene Notification
     * @param notID Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon Integer Id des Icons
     * @return Notification Gibt die Notification zurück
     */
    public Notification buildCustomNot(int notID, String channelid, int icon) {
        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);
        expandedView.setOnClickPendingIntent(R.id.not_expan_Img, buildContentIntent());
        return new NotificationCompat.Builder(context,channelid)
                .setSmallIcon(icon)
                .setCustomContentView(collapsedView) //kleiner Status
                .setCustomBigContentView(expandedView)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle()) // Das nur machen wenn ein konsistenter aussehen mit den restlichen Notifications ereicht werden soll
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }
}