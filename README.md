# Notification Demo App

Demo App für Android Push-Notifications.
## Beschreibung

Dieses Repository enthällt den Code für eine Demonstrations Anwendung für Android Push-Notifications. Diese Anwendung zeigt anschaulich was mit den Push-Notifications ereicht werden kann.

## Inhalt

- Android-Studio Sourcefiles
- Javadoc
- PowerPoint Präsentation mit Code Beispielen
- APK-Datei zur Installation auf Android Geräten


## Ordnerstruktur
- Dokumentation:
    - enthällt die Projekt-Dokumentation.
    - Problems.txt: darin sind aufgetretene Probleme und deren Lösungen aufgeführt.
    - Arbeitszeit.xlsx: Excel Datei zum festhalten der Arbeitszeit.
- Dokumentation/JavaDoc:
    - enthällt das JavaDoc der App.
- Dokumentation/Planung:
    - enthällt die Datei, wo erstmalig aufgeschrieben wurde was umgesetzt werden soll.
- Dokumentation/Präsentation:
    - enthällt die Präsentation mit den Code Beispielen.
- Programm:
    - enthällt die Android Sourcefiles.
    - ordnerstruktur ist die gleiche wie bei einem normalen Android-Studio Projekt.

## Notifications Codebeispiele
Alle Code-teile finden sich in der Klasse NotificationController. Zur besseren Übersicht wurden die Einzelkomponenten hier aufgeführt.  
Zu beachten ist das hier eine Member Variable verwendet wird.
- notificationManager: Typ NotificationManagerCompat
Ebenfalls müssen für eigenimplementation die Jeweiligen Intents, Key-Strings und Actions geändert werden.  

### Default-Notification mit Action-Buttons
Erstellung einer einfachen Push-Notification mit Titel, Tab-Action, Inhalt's Text, Icon und einem Action-Button.  
Es muss bei der Action kein Icon spezifiziert werden. Bei der Unterscheidung der PendingIntent's aufpassen.  
Diese müssen eindeutig Unterscheidbar sein. Zb. eine eindeutige Action.  
**Code:** 
```
Intent contentIntent = new Intent(context, CallActivity.class);
contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
Intent dismissIntent = new Intent(context, NotificationReceiver.class);
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
```

### Progress bar Notification
Erstellung einer Notification mit integrierter Progress bar.  
**Code:**
```
NotificationCompat.Builder progressNot = new NotificationCompat.Builder(context, channelid)
        .setSmallIcon(icon)
        .setContentTitle(context.getString(R.string.NotProgTitle))
        .setContentText(context.getString(R.string.NotProgIndit))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setOngoing(true)
        .setProgress(progressMax, 0, false);
notificationManager.notify(notID, progressNot.build());
```

### Expandable Notification
Erstellung einer erweiterbaren Notification, mit großem Bild.  
**Code:**
```
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
```

### BigPictureStyle Notification
Erstellung einer BigPictureStyle Notification.  
**Code:**
```
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
```

### Media-Controls Notification
Erstellung einer Notification zur Steuerung von Media Inhalten.  
!! Zu beachten ist die richtige importierung in build.gradle.  
Ab Android Oreo kann eine MediaSession hinzugefügt werden, dadurch wird die gesammte Notification in der Farbe des Albumcovers eingefärbt.  
**Code**
```
notbuilder = new NotificationCompat.Builder(context, channelid)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .addAction(R.drawable.ic_volume_on, context.getString(R.string.NotExpAMute),null)
        .addAction(R.drawable.ic_prev, context.getString(R.string.NotExpAPrev), null)
        .addAction(R.drawable.ic_pause, context.getString(R.string.NotExpAPause), null)
        .addAction(R.drawable.ic_next, context.getString(R.string.NotExpANext), null)
        .addAction(R.drawable.ic_close, context.getString(R.string.NotExpAAbort), null)
        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
              //.setMediaSession(mySession)) //An dieser Stelle wird auf eine vorhandene MediaSession referenziert
                .setShowActionsInCompactView(1,2,3)) //diese Integer beziehen sich auf die Reihenfolge der Action Buttons
        .setContentTitle(context.getString(R.string.NotExpanTitle))
        .setContentText(context.getString(R.string.NotExpanText))
        .setLargeIcon(logo)
        .setSmallIcon(icon);
notificationManager.notify(notID, notbuilder.build());
```

### Messaging-Style und Reply Button
Erstellen einer Messaging-Style Notification mit Antwortmöglichkeit(siehe WhatsApp).  
Diese erfordert weitere Einstellungen um zu Funktionnieren. Wichtig ist das der ConversionTitle im Style nicht für Chats   
unter drei Personen verwendet werden soll. Ebenfalls wichtig ist bei den PendingIntent des RemotInput immer einzigartig ist, sonnst  
könnte es sein das der User einem anderen Chat die Nachricht schreibt als er annimmt.
**Code:**
RemotInput
---
```
//RemoteInput, anhand dessen wird er eingegebene Text später entnommen
RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXTRPLY)
        .setLabel(context.getString(R.string.NotRplyLabel))
        .build();
```
ActionButton mit RemoteInput
---
```
Intent rplyIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_REPLY).putExtra(PAYLOAD,notID);
PendingIntent rplyPendingIntent = PendingIntent.getBroadcast(context, notID, rplyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
NotificationCompat.Action rplyAction = new NotificationCompat.Action.Builder(R.drawable.ic_reply, context.getString(R.string.NotActionReply), rplyPendingIntent )
        .addRemoteInput(remoteInput)
        .build();
```
MessagingStyle
---
```
//MessangingStyle, wichtig hier das Person Objekt. Nur eine Charsequence ist in der alten Funktion.
NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(me)
        .setConversationTitle(context.getString(R.string.MessageTitle));
messagingStyle.addMessage(notMessage); // Fügt die Nachricht dem Style hinzu
```
Notification
---
```
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
```

## Mitwirkende

Simon Fentzl

## License
```
This Projekt is licensed under the GNU General Public License v3.0
```
(C) 2021 Simon Fentzl
