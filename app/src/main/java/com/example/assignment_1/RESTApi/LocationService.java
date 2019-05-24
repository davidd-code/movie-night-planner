package com.example.assignment_1.RESTApi;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;
import com.google.android.gms.location.LocationResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;
import static com.example.assignment_1.model.EventModel.events;

public class LocationService extends BroadcastReceiver {
    int notificationPeriod;
    Location currentLocation;

    public LocationService() {

    }

    public LocationService(int minutes) {
        this.notificationPeriod = minutes;
    }


//    Location currentLocation;

    @Override
    public void onReceive(final Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            private Location currentLocation;

            @Override
            public void onLocationChanged(Location location) {
                this.currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        if (intent != null) {
            final String action = intent.getAction();

//            LocationResult locationResult = LocationResult.extractResult(intent);
//            if(locationResult != null) {
            if(locationManager != null) {
                final Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                StringBuilder sb = new StringBuilder("");
                sb.append(currentLocation.getLatitude());
                sb.append(", ");
                sb.append(currentLocation.getLongitude());

//                String locationString = sb.toString();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LocalDateTime currentTime = LocalDateTime.now();
                            for(EventImpl element: events) {
//                                HttpURLConnectionAsyncTask connect = (HttpURLConnectionAsyncTask) new HttpURLConnectionAsyncTask(currentLocation, element.getLatitude(), element.getLongitude()).execute();
                                new HttpURLConnectionAsyncTask(currentLocation, element.getLatitude(), element.getLongitude()).execute();
//                                long travelTime = connect.getTravelTimeSeconds();
                                long travelTime = 0;
                                long minutesUntilEvent = ChronoUnit.MINUTES.between(element.getStartDate(), currentTime);

                                if(minutesUntilEvent - travelTime < notificationPeriod) {
                                    displayNotification(context, element.getTitle(), "is starting in " + minutesUntilEvent + " minutes. Approximately " + travelTime + " travel time.");
                                }

                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                thread.start();
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
