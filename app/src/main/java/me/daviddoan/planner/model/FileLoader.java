package me.daviddoan.planner.model;
import android.content.Context;
import android.content.res.Resources;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class loads the files from movie.txt and events.txt and generates movie and event objects
 * which will then be added into the application. The text files are stored in the assets directory
 */
public class FileLoader {
    private Context mContext;
    InputStream eventFileName, movieFileName;

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
            List<String> linesRead = new LinkedList<>();

            while((line = br.readLine()) != null) {
                if(!line.contains("//")) {
                    linesRead.add(line);
                }
            }

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
            br.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }


    public void loadMovies(ArrayList<MovieImpl> movieList) {

        String line = null;
        // Movie Strings
        String id, title, year, poster;

        try {
            movieFileName = mContext.getAssets().open("movies.txt");
            InputStreamReader isr = new InputStreamReader(movieFileName);
            BufferedReader br = new BufferedReader(isr);
            List<String> linesRead = new LinkedList<>();

            while((line = br.readLine()) != null) {
                if(!line.contains("//")) {
                    linesRead.add(line);
                }
            }

            for(String element: linesRead) {
                String[] stringArray;
                stringArray = element.split("\"");
                id = stringArray[1];
                title = stringArray[3];
                year = stringArray[5];
                poster = stringArray[7];

                MovieImpl readMovie = new MovieImpl(id, title, year, poster);
                movieList.add(readMovie);
            }
            br.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
