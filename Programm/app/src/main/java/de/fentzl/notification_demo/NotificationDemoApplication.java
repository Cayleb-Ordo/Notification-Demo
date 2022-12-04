/*<Notification-Demo>
        Copyright (C) <2021>  <Simon Fentzl>

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/.>*/
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
 * @version 1
 */
public class NotificationDemoApplication extends Application {
    public static final String debugTag = "NotDemo";
    public static Bitmap rwu_logo;
    public static Bitmap mausi_logo;
    static List<Message> MESSAGES = new ArrayList<>();
    private static NotificationBuilder notBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        notBuilder = new NotificationBuilder(getApplicationContext());
        rwu_logo = BitmapFactory.decodeResource(getResources(), R.drawable.rwu_logo);
        mausi_logo = BitmapFactory.decodeResource(getResources(), R.drawable.mausi);
        String annKey = "de.fentzl.anna";
        MESSAGES.add(new Message(getString(R.string.MessageMoring),new Person.Builder().setName(getString(R.string.MessageAnna)).setKey(annKey).build()));
        MESSAGES.add(new Message(getString(R.string.MessageAnswer1),null));
        String richardKey = "de.fentzl.richard";
        MESSAGES.add(new Message(getString(R.string.MessageAnswer2),new Person.Builder().setName(getString(R.string.MessageRichard)).setKey(richardKey).build()));
        NotificationController notificationController = new NotificationController(this.getApplicationContext());
        notificationController.createNotificationChannels(true);
    }

    /**
     * Gibt den Notification Builder zurück
     * @return Referenz auf den internen Notification Builder
     */
    public static NotificationBuilder getNotBuilder() {
        return notBuilder;
    }
}
