package com.example.assignment_1.restapi;

import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.example.assignment_1.restapi.LocationService.SERVICE_CHANNEL;

public class HttpURLConnectionThread extends Thread {

    public static final String TAG = "HttpThread";
    private Location currentLocation;
    private EventImpl currentEvent;
    private int notificationPeriod;
    private Context context;

    public HttpURLConnectionThread(Context context, Location currentLocation, EventImpl event, int period) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.currentEvent = event;
        this.notificationPeriod = period;
    }

    @Override
    public void run() {
        try {

            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isBefore(currentEvent.getStartDate())) {
                HttpURLConnectionAsyncTask connect = (HttpURLConnectionAsyncTask) new HttpURLConnectionAsyncTask(currentLocation, currentEvent.getLatitude(), currentEvent.getLongitude()).execute();

                long travelTime;
                do {
                    travelTime = connect.getTravelTimeSeconds();
                } while(travelTime == 0);

                long minutesUntilEvent = ChronoUnit.MINUTES.between(currentTime, currentEvent.getStartDate());
                travelTime *= 60 * 1000;
                long millisecondsUntilEvent = ChronoUnit.MILLIS.between(currentTime, currentEvent.getStartDate());
                Log.d(TAG, "travelTime: " + travelTime);
//                if(minutesUntilEvent - travelTime < notificationPeriod) {
                if(millisecondsUntilEvent - travelTime < notificationPeriod) {
                    displayNotification(this.context, currentEvent.getTitle(), "is starting in " + minutesUntilEvent + " minutes. Approximately " + travelTime + " travel time.", Integer.parseInt(currentEvent.getID()));
                }
            }



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

        private void displayNotification(Context context, String title, String message, int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SERVICE_CHANNEL)
                .setSmallIcon(R.drawable.ic_explore_white)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setChannelId(SERVICE_CHANNEL);

        notificationManager.notify(id, builder.build());
    }
}
