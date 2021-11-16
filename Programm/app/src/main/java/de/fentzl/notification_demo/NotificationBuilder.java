package de.fentzl.notification_demo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

public class NotificationBuilder {
    public static final String ACTION_DISMISS = "de.fentzl.notification_demo.DISMISS";
    public static final String ACTION_REPLY = "de.fentzl.notification_demo.REPLY";
    public static final String ACTION_MUTE = "de.fentzl.notificatin_demo.MUTE";
    public static final String KEY_TEXTRPLY = "reply";
    public static final String PAYLOAD = "payload";
    private final String CLASS_NOTIFICATIONBUILDER = "de.fentzl.notification_demo.NotificationBuilder";
    private final String personKey = "de.fentzl.me";
    private final Person me;
    private final Context context;
    private final NotificationManagerCompat notificationManager;
    private Notification mediaConNot;

    /**
     * Konstruktor, damit der Notification Manager mit context initialisiert werden kann
     * @param context Aplikations-kontext
     */
    public NotificationBuilder(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        this.me = new Person.Builder().setName(context.getString(R.string.MessageMe)).setKey(personKey).build();
    }


    public PendingIntent buildContentIntent(){
        Intent contentIntent = new Intent(context, CallActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int contentRqC = 1;
        return PendingIntent.getActivity(context, contentRqC, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent buildDismissIntent(int notID){
        /*Sollte man nicht den PendingIntent variiern, zb bei unterschiedlicher RequestID, kann man den normalen Intent unterscheidbar machen. Zb eine Eindeutige Action zuweisen.
        dismissIntent.setAction(ACTION_DISMISS + notCH1).putExtra(PAYLOAD,notCh1);
         */
        Intent dismissIntent = new Intent(context, NotificationReceiver.class)
                .setAction(ACTION_DISMISS).putExtra(PAYLOAD, notID);
        return PendingIntent.getBroadcast(context, notID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    /**
     * Erstellt eine Default Nachricht mit übergebbaren Kanaleinstellungen
     *
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     */
    public Notification buildDefaultNot(int notID, String channelid, String contentTitle, int icon) {
        Notification defaultNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotDefContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(buildContentIntent())
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setAutoCancel(true) // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
                .build();
        return defaultNot;
    }

    /**
     * Erstellt eine Progressbar-Notification und startet einen Thread zur Aktualisierung der Notification
     *  @param notID     Integer Individuelle Notification ID
     * @param channelid String Notification-Kanal identifizierung
     * @param icon      Integer Id des Icons
     * @return
     */
    public NotificationCompat.Builder buildProgressbarNot(int notID, String channelid, int icon) {
        NotificationCompat.Builder progressNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(context.getString(R.string.NotProgTitle))
                .setContentText(context.getString(R.string.NotProgIndit))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(buildContentIntent())
                .setProgress(NotificationController.progressMax, 0, false);
        return progressNot;
    }

    /**
     * Erstellt eine Vergrößerbare Nachricht mit übergebbaren Kanaleinstellungen
     *
     * @param notID        Integer Individuelle Notification ID
     * @param channelid    String Notification-Kanal identifizierung
     * @param contentTitle String Titel der Notification
     * @param icon         Integer Id des Icons
     */
    public Notification buildExpandableNot(int notID, String channelid, String contentTitle, int icon) {
        Notification expandNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentTitle(contentTitle)
                .setContentText(context.getString(R.string.NotPicContent))
                .setContentIntent(buildContentIntent())
                .setLargeIcon(NotificationDemoApplication.rwu_logo)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(NotificationDemoApplication.rwu_logo).bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .setAutoCancel(true)
                .build();
        return expandNot;
    }

    public Notification buildMediaConNot(int notID, String channelid, int icon) {
        Intent muteIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_MUTE).putExtra(PAYLOAD, notID);
        PendingIntent mutePendingIntent = PendingIntent.getBroadcast(context, notID, muteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mediaConNot= new NotificationCompat.Builder(context, channelid)
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
                .setSmallIcon(icon).build();
        return mediaConNot;
    }

    public Notification buildBigTextStyleNot(int notID, String channelid, String contentTitle, int icon) {
        Notification bigTxtNot = new NotificationCompat.Builder(context, channelid)
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
                .build();
        return bigTxtNot;
    }

    public Notification buildDirRplyMessStNot(int notID,String channelid, int icon) {
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
                .setConversationTitle(context.getString(R.string.MessageTitle));
        for (Message chatMessage : NotificationDemoApplication.MESSAGES){
            NotificationCompat.MessagingStyle.Message notMessage = new NotificationCompat.MessagingStyle.Message(
                    chatMessage.getText(),
                    chatMessage.getTimestamp(),
                    chatMessage.getSender()
            );
            messagingStyle.addMessage(notMessage); // Fügt die Nachricht dem Style hinzu
        }
        //Eigentlich Notification bauen
        Notification rplyNot = new NotificationCompat.Builder(context, channelid)
                .setSmallIcon(icon)
                .setContentIntent(buildContentIntent())
                .setStyle(messagingStyle)
                .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
                .addAction(rplyAction)
                .setColor(context.getColor(R.color.Primary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        return rplyNot;
    }

    public Notification buildCustomNot(int notID, String channelid, int icon) {
        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);

        Notification customNot = new NotificationCompat.Builder(context,channelid)
                .setSmallIcon(icon)
                .setCustomContentView(collapsedView) //kleiner Status
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle()) // Das nur machen wenn ein konsistenter aussehen mit den restlichen Notifications ereicht werden soll
                .build();
        return customNot;
    }
}