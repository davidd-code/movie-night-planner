package com.example.assignment_1.RESTApi;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;
import com.google.android.gms.location.LocationResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.example.assignment_1.model.EventModel.events;

public class LocationService extends BroadcastReceiver {
    int notificationPeriod;

    public LocationService() {

    }

    public LocationService(int minutes) {
        this.notificationPeriod = minutes;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        if(intent != null) {
            LocationResult locationResult = LocationResult.extractResult(intent);
            if(locationResult != null) {
                final Location currentLocation = locationResult.getLastLocation();
                StringBuilder sb = new StringBuilder("");
                sb.append(currentLocation.getLatitude());
                sb.append(", ");
                sb.append(currentLocation.getLongitude());

                String locationString = sb.toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocalDateTime currentTime = LocalDateTime.now();
                        for(EventImpl element: events) {
                            HttpURLConnectionAsyncTask connect = new HttpURLConnectionAsyncTask(currentLocation, element.getLatitude(), element.getLongitude());
                            long travelTime = connect.getTravelTimeSeconds();
                            long minutesUntilEvent = ChronoUnit.MINUTES.between(element.getStartDate(), currentTime);

                            if(minutesUntilEvent - travelTime < notificationPeriod) {
                                displayNotification(context, element.getTitle(), "is starting in " + minutesUntilEvent + " minutes. Approximately " + travelTime + " travel time.");
                            }

                        }
                    }
                }).start();
            }
        }
    }

    private void displayNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                .setSmallIcon(R.drawable.ic_explore_white)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setChannelId("channel1");

        notificationManager.notify(1, builder.build());

    }
}
