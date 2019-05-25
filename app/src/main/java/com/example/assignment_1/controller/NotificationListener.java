package com.example.assignment_1.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;

import com.example.assignment_1.R;

public class NotificationListener extends ContextWrapper {

    public static final String channelID = "channelID";
    public static final String channelName = "default channel";

    private NotificationManager mManager;

    public NotificationListener(Context base) {
        super(base);
        createChannel();
    }

    public void createChannel() {
        NotificationChannel channel1 = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(channel1);
    }

    public NotificationManager getNotificationManager() {
        if(mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_explore_white);
    }
}
