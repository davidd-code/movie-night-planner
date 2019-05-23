package com.example.assignment_1.controller;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionAsyncTask extends AsyncTask<String, Void, Void> {

    private StringBuilder htmlStringBuilder = new StringBuilder();
    public static final String TEST_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w"
    @Override
    protected String doInBackground(HttpCall... httpCalls) {

    }

    private String getData(HashMap<String, String> parameters, int methodType) {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;

        for(Map.Entry<String, String> entry: parameters.entrySet()) {
            if(isFirst) {
                isFirst = false;
                if(methodType == HttpCall.GET) {
                    result.append("?");
                }
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
        }

    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection connection = null;
        HttpCall httpCall = httpCalls[0];
        try {
            String dataParams = getData(httpCall.getParams(), httpCall.getMethodtype());
            URL url = new URL(httpCall.getMethodtype() = HttpCall.GET);
            connection = (HttpURLConnection) url.openConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
