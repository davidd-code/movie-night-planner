package com.example.assignment_1.RESTApi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.example.assignment_1.model.EventImpl;
import com.google.android.gms.location.LocationResult;

import java.time.LocalDateTime;
import java.util.Calendar;

import static com.example.assignment_1.model.EventModel.events;

public class LocationService extends BroadcastReceiver {
    int recallAlarm;

    public LocationService(int milliSeconds) {
        this.recallAlarm = milliSeconds;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null) {
            LocationResult locationResult = LocationResult.extractResult(intent);
            if(locationResult != null) {
                final Location currentLocation = locationResult.getLastLocation();
                StringBuilder sb = new StringBuilder("");
                sb.append(currentLocation.getLatitude());
                sb.append(", ");
                sb.append(currentLocation.getLongitude());

                String locationString = sb.toString();

                Thread newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocalDateTime currentTime = LocalDateTime.now();
                        for(EventImpl element: events) {
                        }
                    }
                }).start();
            }
        }
    }
}
