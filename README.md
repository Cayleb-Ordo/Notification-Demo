# Notification Demo App

Demo App für Android Push-Notifications.
## Beschreibung

Dieses Repository enthällt den Code für eine Demonstrations Anwendung für Android Push-Notifications. Diese Anwendung zeigt anschaulich   
was mit den Push-Notifications ereicht werden kann.

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
Zu beachten ist das hier zwei member Variablen verwendet werden.
- notbuilder: Typ NotificationCompat.Builder
- notificationManager: Typ NotificationManagerCompat

### Default-Notification mit Action-Buttons
Erstellung einer einfachen Push-Notification mit Titel, Tab-Action, Inhalt's Text, Icon und einem Action-Button.  
**Code:** 
```
notbuilder = new NotificationCompat.Builder(context, channelid)
        .setSmallIcon(icon)
        .setContentTitle(contentTitle)
        .setContentText(context.getString(R.string.NotContent))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingcontentInt)
        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
        .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
notificationManager.notify(notID, notbuilder.build());
```

### Progress bar Notification
Erstellung einer Notification mit integrierter Progress bar.  
**Code:**
```
notbuilder = new NotificationCompat.Builder(context, channelid)
        .setSmallIcon(icon)
        .setContentTitle(contentTitle)
        .setContentText(context.getString(R.string.NotContent))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingcontentInt)
        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), dismissPendingIntent)
        .setAutoCancel(true); // Lässt die Nachricht nicht verschwinden bis auf sie geklickt wird
notificationManager.notify(notID, builder.build());
```

### Expandable Notification
Erstellung einer erweiterbaren Notification, mit großem Bild.  
**Code:**
```
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
```

### Media-Controls Notification
Erstellung einer Notification zur Steuerung von Media Inhalten.  
**Code**
```

```

## Mitwirkende

Simon Fentzl

## License
```
This Projekt is licensed under the GNU General Public License v3.0
```
(C) 2021 Simon Fentzl
