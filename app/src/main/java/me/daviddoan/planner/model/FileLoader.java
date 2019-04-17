package me.daviddoan.planner.model;
import android.content.Context;
import android.content.res.Resources;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {
    private Context mContext;
    String movieFileName;
//    String eventFileName = "C:\\Users\\ddoan\\OneDrive\\RMIT\\2019\\sem1\\Mobile Application Development\\a1\\app\\src\\main\\assets\\events.txt";
//    String eventFileName;
    InputStream eventFileName;

    public FileLoader(Context context) {
        this.mContext = context;
    }

    public void loadEvents(ArrayList<EventImpl> eventList) {

        String line = null;
        String id, title, startDate, endDate, venue, location;

        try {
            eventFileName = mContext.getAssets().open("events.txt");
            InputStreamReader isr = new InputStreamReader(eventFileName);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            List<String> linesRead = new LinkedList<>();

            while((line = br.readLine()) != null) {
                if(!line.contains("//")) {
                    linesRead.add(line);
                }
            }
//            FileReader file = new FileReader(eventFileName);
//            BufferedReader textReader = new BufferedReader(file);
//            List<String> linesRead = new LinkedList<String>();
//            while((line = textReader.readLine()) != null) {
//                if(!line.contains("//")) {
//                    linesRead.add(line);
//
//                }
//            }

            for(String element: linesRead) {
                String[] stringArray;
                stringArray = element.split("\"");
                id = stringArray[1];
                title = stringArray[3];
                startDate = stringArray[5];
                endDate = stringArray[7];
                venue = stringArray[9];
                location = stringArray[11];

                EventImpl readEvent = new EventImpl(id, title, startDate, endDate, venue, location, null);
                eventList.add(readEvent);
            }
//            textReader.close();
        }
        catch(FileNotFoundException e) {

        }
        catch(IOException ex) {

        }
    }
}
