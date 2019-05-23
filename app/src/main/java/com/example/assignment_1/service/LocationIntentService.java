package com.example.assignment_1.service;

import android.app.IntentService;
import android.content.Intent;

public class LocationIntentService extends IntentService {

    private static final String TAG = "LocationIntentService";

    public LocationIntentService() {
        super("LocationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
