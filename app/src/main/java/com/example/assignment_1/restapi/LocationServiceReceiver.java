package com.example.assignment_1.restapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.View;

public class LocationServiceReceiver extends BroadcastReceiver {
    int notificationPeriod;

    public LocationServiceReceiver() {

    }

    public LocationServiceReceiver(int minutes) {
        this.notificationPeriod = minutes;
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, LocationService.class);
        serviceIntent.putExtra("notificationPeriod", notificationPeriod);
        context.startService(serviceIntent);

    }

}
