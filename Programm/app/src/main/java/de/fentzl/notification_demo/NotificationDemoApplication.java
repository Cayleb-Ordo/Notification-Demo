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

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Haupt-Applicationklasse
 * @author Simon Fentzl
 * @version 2
 */
public class NotificationDemoApplication extends Application {
    public static final String debugTag = "NotDemo";
    public static Bitmap rwu_logo;
    public static Bitmap mausi_logo;
    private static NotificationDemoApplication APPLICATION;
    private final List<Message> MESSAGES = new ArrayList<>();
    private NotificationController notController;
    private NotificationBuilder notBuilder;
    private int idCounter = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
        notBuilder = new NotificationBuilder(getApplicationContext());
        rwu_logo = BitmapFactory.decodeResource(getResources(), R.drawable.rwu_logo);
        mausi_logo = BitmapFactory.decodeResource(getResources(), R.drawable.mausi);
        notController = new NotificationController(this.getApplicationContext());
        notController.createNotificationChannels(true);
        String annKey = "de.fentzl.anna";
        MESSAGES.add(new Message(getString(R.string.MessageMoring), new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1), null));
        String richardKey = "de.fentzl.richard";
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2), new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
    }

    /**
     * Gibt die Referenz auf sich selbst zurück
     * @return  Referenz auf den Singleton
     */
    public static NotificationDemoApplication getAPPLICATION() { return APPLICATION; }

    /**
     * Gibt die Liste der Nachrichten zurück
     * @return Liste der Nachrichten
     */
    public List<Message> getMESSAGES() {
        return MESSAGES;
    }

    /**
     * Gibt den NotificationController zurück
     * @return Referenz auf den Controller
     */
    public  NotificationController getNotController() { return notController; }

    /**
     * Gibt den Notification Builder zurück
     * @return Referenz auf den internen Notification Builder
     */
    public  NotificationBuilder getNotBuilder() {
        return notBuilder;
    }

    /**
     * Gibt den globalen ID-Zähler zurück
     * @return ID-Counter, beginnend bei 0
     */
    public int getIdCounter() {
        return idCounter;
    }

    /**
     * Setzt den globalen ID-Zähler auf den übergebenen Wert
     * @param idCounter Neuer Wert des ID-Zählers
     */
    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}
