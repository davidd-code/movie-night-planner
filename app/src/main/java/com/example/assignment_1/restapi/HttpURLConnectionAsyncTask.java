package com.example.assignment_1.restapi;

import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnectionAsyncTask extends AsyncTask<String, String, String> {

    private Location currentLocation;
    private Double destinationLatitude, destinationLongitude;
//    private BufferedReader br = null;
//    private HttpURLConnection connection = null;
    private static BufferedReader br = null;
    private static HttpURLConnection connection = null;
    private String duration;
    private long seconds;

    public HttpURLConnectionAsyncTask(Location location, Double latitude, Double longitude) {
        this.currentLocation = location;
        this.destinationLatitude = latitude;
        this.destinationLongitude = longitude;
    }

    public static String requestURL(Location currentLocation, Double destinationLatitude, Double destinationLongitude) {
        String key = "AIzaSyDN4UvDycELwsIbLsF6CmPXgUtmPlbEU4w";
        return "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destinations=" + destinationLatitude + "," + destinationLongitude + "&mode=driving&key=" + key;
    }

//    private static long calculateTravelTimeSeconds(String durationString) {
//        String[] splitString = durationString.split(" ");
//        long seconds = 0;
//        if(splitString.length == 2) {
//            int minutes = Integer.parseInt(splitString[0]);
//            seconds = minutes * 60;
//        } else if(splitString.length == 4) {
//            int hours = Integer.parseInt(splitString[0]);
//            int minutes = Integer.parseInt(splitString[2]);
//            seconds = (hours * 60 * 60) + (minutes * 60);
//        }
//        return seconds;
//    }

    private static long calculateTravelTimeMinutes(String durationString) {
        String[] splitString = durationString.split(" ");
        long minutes = 0;
        if(splitString.length == 2) {
            minutes = Integer.parseInt(splitString[0]);
        } else if(splitString.length == 4) {
            long hours = Integer.parseInt(splitString[0]);
            minutes += hours * 60;
            minutes += Integer.parseInt(splitString[2]);
        }
        return minutes;
    }


    public long getTravelTimeSeconds() {
        return seconds;
    }

    public static long getTravelTimeSeconds(Location location, double latitude, double longitude) {
        String urlString = requestURL(location, latitude, longitude);
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            String finalJSON = stringBuffer.toString();
            JSONObject parentObject = new JSONObject(finalJSON);
            JSONArray rowsArray = parentObject.getJSONArray("rows");
            JSONObject elementsArray = rowsArray.getJSONObject(0);
            JSONArray childArray = elementsArray.getJSONArray("elements");
            JSONObject durationObject = childArray.getJSONObject(0);
            JSONObject durationElement = durationObject.getJSONObject("duration");
            String durationString = durationElement.getString("text");

//            return calculateTravelTimeSeconds(durationString);
            return calculateTravelTimeMinutes(durationString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
        return 0;
    }

    @Override
    protected String doInBackground(String... strings) {
//        String urlString = requestURL(currentLocation, destinationLatitude, destinationLongitude);
//        try {
//            URL url = new URL(urlString);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//
//            InputStream is = connection.getInputStream();
//            br = new BufferedReader(new InputStreamReader(is));
//
//            String line = "";
//            StringBuilder stringBuffer = new StringBuilder();
//            while((line = br.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//            String finalJSON = stringBuffer.toString();
//            JSONObject parentObject = new JSONObject(finalJSON);
//            JSONArray rowsArray = parentObject.getJSONArray("rows");
//            JSONObject elementsArray = rowsArray.getJSONObject(0);
//            JSONArray childArray = elementsArray.getJSONArray("elements");
//            JSONObject durationObject = childArray.getJSONObject(0);
//            JSONObject durationElement = durationObject.getJSONObject("duration");
//            String durationString = durationElement.getString("text");
//
//            this.seconds = calculateTravelTimeSeconds(durationString);
//            return durationString;
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            if(connection != null) {
//                connection.disconnect();
//            }
//            try {
//                if(br != null) {
//                    br.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }

    public String getDuration() {
        return this.duration;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.duration = s;
        seconds = calculateTravelTimeMinutes(s);
        System.out.println(s);
    }
}
