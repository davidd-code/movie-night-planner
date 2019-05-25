package com.example.assignment_1.restapi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
                Log.d(TAG, "travelTime: " + travelTime);
                if(minutesUntilEvent - travelTime < notificationPeriod) {
                    displayNotification(this.context, currentEvent.getTitle(), "is starting in " + minutesUntilEvent + " minutes. Approximately " + travelTime + " travel time.", Integer.parseInt(currentEvent.getID()));
                }
            }



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNotification(Context context, String title, String message, int id) {

        Intent remindIntent, dismissIntent, cancelEventIntent;
        remindIntent = new Intent(context, RemindNotificationReceiver.class);
        remindIntent.putExtra("channel_ID", id);
        dismissIntent = new Intent(context, DismissNotificationReceiver.class);
        remindIntent.putExtra("channel_ID", id);
        cancelEventIntent = new Intent(context, CancelEventNotificationReceiver.class);
        remindIntent.putExtra("channel_ID", id);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SERVICE_CHANNEL)
                .setSmallIcon(R.drawable.ic_explore_white)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_remind_later, "Remind", getPendingIntent(id, remindIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                .addAction(R.drawable.ic_dismiss, "Dismiss", getPendingIntent(id, dismissIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                .addAction(R.drawable.ic_cancel_event, "Cancel", getPendingIntent(id, cancelEventIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setChannelId(SERVICE_CHANNEL);

        notificationManager.notify(id, builder.build());
    }

    private PendingIntent getPendingIntent(int id, Intent intent, int flag) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, flag);
        return pendingIntent;
    }
}
