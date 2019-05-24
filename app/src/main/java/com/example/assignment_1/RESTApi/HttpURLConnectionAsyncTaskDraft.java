package com.example.assignment_1.RESTApi;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionAsyncTaskDraft extends AsyncTask<HttpCall, String, String> {

    private StringBuilder htmlStringBuilder = new StringBuilder();

//    public static final String TEST_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w";
    public static String requestURL(Location currentLocation, Double destinationLatitude, Double destinationLongitude) {
        String key = "AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w";
        return "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destinations=" + destinationLatitude + "," + destinationLongitude + "&mode=driving&key=" + key;
    }

    private WeakReference<Activity> activity;

//    public HttpURLConnectionAsyncTaskDraft(Activity activity) {
//        this.activity = new WeakReference<>(activity);
//    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onResponse(s);
    }

    @Override
    protected String doInBackground(HttpCall... httpCalls) {
        HttpURLConnection urlConnection = null;
        HttpCall httpCall = httpCalls[0];
        StringBuilder response = new StringBuilder();
        try {
            String dataParams = getDataString(httpCall.getParams(), httpCall.getMethodtype());
            URL url = new URL(httpCall.getMethodtype() == HttpCall.GET ? httpCall.getUrl() + dataParams : httpCall.getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(httpCall.getMethodtype() == HttpCall.GET ? "GET" : "POST");
            if(httpCall.getParams() != null && httpCall.getMethodtype() == HttpCall.POST) {
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.append(dataParams);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }
        int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public void onResponse(String response) {

    }
    private String getDataString(HashMap<String, String> params, int methodType) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        for(Map.Entry<String, String> entry: params.entrySet()) {
            if(isFirst) {
                isFirst = false;
                if(methodType == HttpCall.GET) {
                    result.append("?");
                }
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
