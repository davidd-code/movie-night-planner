package com.example.assignment_1.restapi;

import android.location.Location;
import android.util.Log;

import com.example.assignment_1.model.EventImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HttpURLConnectionThread extends Thread {

    public static final String TAG = "HttpThread";
    private Location currentLocation;
    private EventImpl currentEvent;

    public HttpURLConnectionThread(Location currentLocation, EventImpl event) {
        this.currentLocation = currentLocation;
        this.currentEvent = event;
    }

    @Override
    public void run() {
        try {
            LocalDateTime currentTime = LocalDateTime.now();

            HttpURLConnectionAsyncTask connect = (HttpURLConnectionAsyncTask) new HttpURLConnectionAsyncTask(currentLocation, currentEvent.getLatitude(), currentEvent.getLongitude()).execute();
//                        HttpURLConnectionAsyncTask connect = (HttpURLConnectionAsyncTask) new HttpURLConnectionAsyncTask(currentLocation, element.getLatitude(), element.getLongitude()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            long travelTime = connect.getTravelTimeSeconds();
            while(travelTime == 0){
                travelTime = connect.getTravelTimeSeconds();
            }

            long minutesUntilEvent = ChronoUnit.MINUTES.between(currentEvent.getStartDate(), currentTime);
            Log.d(TAG, "travelTime: " + travelTime);
//            if(minutesUntilEvent - travelTime < notificationP) {s
//                displayNotification(getApplicationContext(), element.getTitle(), "is starting in " + minutesUntilEvent + " minutes. Approximately " + travelTime + " travel time.");
//            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
