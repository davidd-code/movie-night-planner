package com.example.assignment_1.restapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationServiceReceiver extends BroadcastReceiver {
    int notificationPeriod;

    public LocationServiceReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, LocationService.class);
        notificationPeriod = intent.getIntExtra("notificationPeriod", 0);
        serviceIntent.putExtra("notificationPeriod", notificationPeriod);
        context.startService(serviceIntent);

    }

}
