package com.example.assignment_1.controller;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpURLConnectionAsyncTask extends AsyncTask<String, Void, Void> {

    private StringBuilder htmlStringBuilder = new StringBuilder();

    public static final String TEST_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w";

    private WeakReference<Activity> activity;

    public HttpURLConnectionAsyncTask(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection connection = null;
//        HttpCall httpCall = httpCalls[0];
        try {
//            String dataParams = getData(httpCall.getParams(), httpCall.getMethodtype());
            URL url = new URL(TEST_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/html");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            int statusCode = connection.getResponseCode();
            if(statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            logHeaders(connection.getHeaderFields());

            readData(connection.getContentLength(), new BufferedReader(new InputStreamReader(connection.getInputStream())));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    // logging headers is different for each approach
    private void logHeaders(Map<String, List<String>> headers)
    {
        StringBuilder sb = new StringBuilder();
        for (String key : headers.keySet())
        {
            sb.append("Header: ").append(key).append(", Values: ");
            for (String value : headers.get(key))
                sb.append(value).append(" ");
            sb.append('\n');
        }
    }

    private void readData(int length, BufferedReader br) throws Exception {
        if(length == -1) {
            length = 50000;
        }

        String line;
        while((line = br.readLine()) != null) {
            htmlStringBuilder.append(line);

        }
    }
//    private String getData(HashMap<String, String> parameters, int methodType) {
//        StringBuilder result = new StringBuilder();
//        boolean isFirst = true;
//
//        for(Map.Entry<String, String> entry: parameters.entrySet()) {
//            if(isFirst) {
//                isFirst = false;
//                if(methodType == HttpCall.GET) {
//                    result.append("?");
//                }
//            } else {
//                result.append("&");
//            }
//            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
//        }

}
