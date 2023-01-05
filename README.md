# Notification Demo App

Demo App für Android Push-Notifications.

## Beschreibung

Dieses Repository enthält den Code einer Demonstrationsanwendung für Android Push-Notifications. Diese Anwendung zeigt anschaulich, was mit den Push-Notifications erreicht werden kann.
Diese App wurde ursprünglich für Android 8(Oreo) mit Java entwickelt, ist aber kompatibel bis Android 13.  

## Inhalt

- [Ordnerstruktur](#ordnerstruktur)
- [Installation](#installation)
- [Android Berechtigungen](#android-berechtigungen)
    - [Berechtigung für Benachrichtigungen Anfragen](#berechtigung-für-benachrichtigungen-anfragen)
    - [Begründen der Anfrage](#begründen-der-anfrage)
    - [Code Beispiel](#code-beispiel)
- [Notifications Codebeispiele](#notifications-codebeispiele)
    - [Default-Notification mit Action-Buttons](#default-notification-mit-action-buttons) 
    - [Progress bar Notification](#progress-bar-notification)
    - [Expandable Notification](#expandable-notification)
    - [BigPictureStyle Notification](#bigpictureStyle-notification)
    - [Media-Controls Notification](#media-controls-notification)
    - [Messaging-Style und Reply Button](#messaging-style-und-reply-button)
    - [Custom](#custom)
- [Offizielle Android Dokumentation](#offizielle-android-dokumentation)
- [Entwickler](#entwickler)
- [Lizenz](#lizenz)

## Ordnerstruktur
- Dokumentation:
    - enthält die Projekt-Dokumentation.
    - Problems.txt: darin sind aufgetretene Probleme und deren Lösungen aufgeführt.
    - Arbeitszeit.xlsx: Excel Datei zum Festhalten der Arbeitszeit.
    - Dokumentation/Grafiken:
        - enthält die verwendeten Grafiken der App und der Projektarbeit.
    - Dokumentation/JavaDoc:
        - enthält das JavaDoc der App.
    - Dokumentation/Planung:
        - enthält die Datei, wo erstmalig aufgeschrieben wurde was umgesetzt werden soll.
        - erster Mockup der App.
    - Dokumentation/Präsentation:
        - enthält die Präsentation mit den Code Beispielen im PDF und PowerPoint Format.
    - Dokumentation/Projektarbeit:
        - enthält die Projektarbeit.
- Programm:
    - enthält die Android Quelldateien.
    - Ordnerstruktur gleich wie bei einem normalen Android-Studio Projekt.
    
## Installation
Zum Installieren am einfachsten das Android-Studio Projekt laden und für die Benötigte Android-Version kompilieren.  
Es wird keine Separate apk-Datei mitgeliefert.

## Android Berechtigungen

Seit Android 6(Marshmallow) gibt es neben den Installationsberechtigungen auch sog. Runtime-Permissions. Dadurch fragt die App, während sie ausgeführt wird, nach den benötigten Berechtigungen. In Android Oreo musste der Benutzer der App nicht gefragt werden, ob die App ihm Benachrichtigungen schicken darf. Denn die Benachrichtigungen sind nicht in einer **Gefährlichen Kategorie**, deshalb ist es nicht relevant explizit zu fragen.   
Das hat sich mit Erscheinen von Android 13 geändert. Nun ist standardmäßig alles deaktiviert, was nicht explizit im Manifest angegeben und dem Benutzer mitgeteilt wurde. Wenn dieser nicht zustimmt, wird die App nicht in der Lage sein, Benachrichtigungen darzustellen. Daher gibt es von Android eine API, mit der man dem Benutzer in einem Popup nach seiner Zustimmung fragen kann. Ein guter Startpunkt sind die offiziellen Dokumentationen.  
Es werden hier nur die relevanten Elemente für die Push-Notification Berechtigung besprochen, die genauen Details zu allen Berechtigungen und deren Auswirkungen sind in der offiziellen Dokumentation enthalten([Android-Permissions](https://developer.android.com/guide/topics/permissions/overview)).

### Berechtigung für Benachrichtigungen Anfragen
**!!!Gilt nur für API 33 und aufwärts!!!**
Zunächst müssen alle Berechtigungen, die die App braucht, in der AndroidManifest Datei eingetragen werden. Für den Fall der Benachrichtigungen wird folgende Zeile gebraucht:

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```
Es gibt zwei Wege, wie die Berechtigung angefragt werden kann. Die einfachere Variante ist es, das Android System selbst machen zu lassen, man kann dies jedoch selbst übernehmen, sofern das einen Vorteil bringt.  
Um zu schauen, ob eine bestimmte Berechtigung gesetzt ist, wird folgende Funktion verwendet: 

```java
checkSelfPermission(Context, Permission)
```

Diese gibt einen einen ```PackageManager.PERMISSION_GRANTED``` zurück, wenn die angeforderte Berechtigung gesetzt ist. Wenn dies der Fall ist, kann die App ihren normalen Ablauf weiterführen, ansonsten muss der Benutzer um die Berechtigung gebeten werden.!! Wichtig diesen Code kann man nur innerhalb eines Fragments oder einer Activity verwenden!!  
Um nun den Nutzer darüber in Kenntnis setzen, dass diese App die spezifische Berechtigung braucht muss man sich eine Referenz auf einen ```ActivityResultLauncher```. Dieser ist der Rückgabewert der Funktion *registerForActivityResult*.  
Dieser Funktion wird ein *ActivityResultContract* und ein *ActivityResultCallback* mitgegeben. Der Contract bezieht sich auf die angeforderte Berechtigung und der *ActivityResultCallback* ist die Funktion, die aufgerufen wird, wenn der Benutzer sich für eine der beiden Optionen des Systemdialogs entschieden hat. Das kann auch eine Lambda Funktion sein. Dieser ResultLauncher wird dann ausgeführt, wenn der Benutzer noch keine Berechtigung eingestellt hat.  

### Begründen der Anfrage

Wenn bei der Installation der App der Benutzer zweimal den Systemdialog mit verweigern ausgewählt hat, wird der Dialog nicht mehr ausgeführt. So wird beim erstmaligen Starten der App nachgefragt, der Benutzer verweigert jedoch. Sollte er die App noch einmal starten, wird wieder nach der Berechtigung gefragt. Wenn er wieder auf Verweigern klickt, ist das für Android so also, ob ein haken bei *nicht erneut fragen* gesetzt wurde.  
Deswegen wir die Funktion *shouldShowRequestPermissionRationale* nur einmalig nach dem ersten Mal verweigern ausgeführt. Diese ermöglicht, dem Benutzer genauer mitzuteilen, warum die Berechtigung das App Erlebnis verbessert.  
Hat der Benutzer wieder die Berechtigung verweigert, so sollte dies Respektiert werden und die App grundsätzlich auch ohne diese ausführbar sein. Dies geht in dem Fall der Notification Demo App nicht, denn dadurch verliert sie ihren Zweck.

### Code Beispiel

```java
if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
    Log.i("NotificationApp", "Berechtigung wurde schon erteilt!");
} else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
    Snackbar.make(mLayout, R.string.AskPerm, Snackbar.LENGTH_INDEFINITE).setAction(R.string.Ok, view -> ActivityCompat.requestPermissions(MainActivity.this,
            new String[]{Manifest.permission.POST_NOTIFICATIONS},
            PERMISSION_REQUEST_NOT)).show();
} else {
    ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Log.i(NotificationDemoApplication.debugTag, "Berechtigung erteilt!");
        } else {
            Log.i(NotificationDemoApplication.debugTag, "Berechtigung nicht erteilt");
        }
    });
    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
}
```

## Notifications Codebeispiele

Alle Codeelemente finden sich in der Klasse NotificationBuilder. Zur besseren Übersicht wurden die Einzelkomponenten hier aufgeführt.  
Ebenfalls müssen für eigen Implementation die jeweiligen Intents, Key-Strings und Actions geändert werden.  
Die verwendeten Android-Librarys sind alle androidx.

### Default-Notification mit Action-Buttons
---
Erstellung einer einfachen Push-Notification mit Titel, Tab-Action, Inhalts Text, Icon und einem Action-Button.  
Es muss bei der Action kein Icon spezifiziert werden. Bei der Unterscheidung der PendingIntent's aufpassen.  
Diese müssen eindeutig unterscheidbar sein, z. B. eine eindeutige Action.  
**Code:** 
```java
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
---
Erstellung einer Notification mit integrierter Progress bar.  
**Code:**
```java
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
---
Erstellung einer erweiterbaren Notification, mit großem Bild.  
**Code:**
```java
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
---
Erstellung einer BigPictureStyle Notification.  
**Code:**
```java
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
---
Erstellung einer Notification zur Steuerung von Media Inhalten.  
!! Zu beachten ist die richtige Importierung in build.gradle.  
Ab Android Oreo kann eine MediaSession hinzugefügt werden, dadurch wird die gesamte Notification in der Farbe des Albumcovers eingefärbt.  
**Code**
```java
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
---
Erstellen einer Messaging-Style Notification mit Antwortmöglichkeit (siehe WhatsApp).  
Diese erfordert weitere Einstellungen um zu Funktionieren. Wichtig ist das der ConversionTitle im Style nicht für Chats   
unter drei Personen verwendet werden soll. Ebenfalls wichtig ist bei das der PendingIntent des RemoteInput immer einzigartig ist, sonst  
könnte es sein das der User einem anderen Chat die Nachricht schreibt als er annimmt.  
**Code:**  
#### RemoteInput
---
```java
//RemoteInput, anhand dessen wird er eingegebene Text später entnommen
RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXTRPLY)
        .setLabel(context.getString(R.string.NotRplyLabel))
        .build();
```
#### ActionButton mit RemoteInput
---
```java
Intent rplyIntent = new Intent(context, NotificationReceiver.class).setAction(ACTION_REPLY).putExtra(PAYLOAD,notID);
PendingIntent rplyPendingIntent = PendingIntent.getBroadcast(context, notID, rplyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
NotificationCompat.Action rplyAction = new NotificationCompat.Action.Builder(R.drawable.ic_reply, context.getString(R.string.NotActionReply), rplyPendingIntent )
        .addRemoteInput(remoteInput)
        .build();
```
#### MessagingStyle
---
```java
//MessangingStyle, wichtig hier das Person Objekt. Nur eine Charsequence ist in der alten Funktion.
NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(me)
        .setConversationTitle(context.getString(R.string.MessageTitle));
messagingStyle.addMessage(notMessage); // Fügt die Nachricht dem Style hinzu
```
#### Notification
---
```java
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

### Custom
---
Erstellt eine Komplett benutzerdefiniert Notification. Es werden Layouts für RemoteViews benötigt.  
Zwei Einschränkungen: CollapsedView(Notification ist klein) höhe max. 64dp. ExpandedView(Erweiterte Notification) höhe 256dp.  
Um einen Konsistentes Aussehen mit den restlichen Notifications zu erreichen sollte man die Notification mit dem NotificationCompat.DecoratedCustomViewStyle versehen.  
**Code:**
```java
RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_collapsed);
RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);
expandedView.setOnClickPendingIntent(R.id.not_expan_Img, buildContentIntent());
Notification customNot = new NotificationCompat.Builder(context,channelid)
        .setSmallIcon(icon)
        .setCustomContentView(collapsedView) //kleiner Status
        .setCustomBigContentView(expandedView)
        .addAction(R.drawable.ic_launcher_foreground, context.getString(R.string.NotActionClose), buildDismissIntent(notID))
        .setStyle(new NotificationCompat.DecoratedCustomViewStyle()) // Das nur machen wenn ein konsistenter aussehen mit den restlichen Notifications ereicht werden soll
        .build();
                
```

## Offizielle Android Dokumentation

- [Notification erstellen](https://developer.android.com/develop/ui/views/notifications/build-notification)
- [Notification Übersicht](https://developer.android.com/develop/ui/views/notifications)

## Entwickler

Simon Fentzl

## Lizenz

This Project is licensed under the GNU General Public License v3.0

&copy; 2021-2023 Simon Fentzl
