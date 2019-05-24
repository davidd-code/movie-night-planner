package com.example.assignment_1.RESTApi;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
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

public class HttpURLConnectionAsyncTask extends AsyncTask<String, String, String> {

    private Location currentLocation;
    private Double destinationLatitude, destinationLongitude;
    private BufferedReader br = null;
    private HttpURLConnection connection = null;

    public HttpURLConnectionAsyncTask(Location location, Double latitude, Double longitude) {
        this.currentLocation = location;
        this.destinationLatitude = latitude;
        this.destinationLongitude = longitude;
    }

    public static String requestURL(Location currentLocation, Double destinationLatitude, Double destinationLongitude) {
        String key = "AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w";
        return "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destinations=" + destinationLatitude + "," + destinationLongitude + "&mode=driving&key=" + key;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder sb = new StringBuilder();
        String urlString = requestURL(currentLocation, destinationLatitude, destinationLongitude);
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

//            InputStream is = new BufferedInputStream(connection.getInputStream());
            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
