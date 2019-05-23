package com.example.assignment_1.model;

import android.content.Context;

import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.viewModel.ContactListAdapter;
import com.example.assignment_1.viewModel.EventListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public final class EventModel {

    private EventModel(){};

    public static ArrayList<EventImpl> events = new ArrayList<EventImpl>();
    public static ArrayList<MovieImpl> movies = new ArrayList<MovieImpl>();
    public static ArrayList<Contact> contacts = new ArrayList<>();
    public static EventListAdapter eventAdapter = new EventListAdapter(events);
    public static ContactListAdapter contactAdapter;
    public static final String JAYG = "]|[::::|:::>>]|[<<:::|::::>>]|[>>>>>>>>>>>>>> ";

    public void addEvent(EventImpl newEvent){
        events.add(newEvent);
    }

    public static void loadEvents(ArrayList<String[]> data) throws ParseException {

        LocalDateTime start, end;

        for (int i = 0; i < data.size(); i++) {
            String[] txtInfo = data.get(i);

            //reformat incorrect txt file format
           //  "d/MM/yyyy h:mm:ss a" into  "dd/MM/yyyy hh:mm:ss a"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            Date date1 = sdf.parse(txtInfo[2]);
            Date date2 = sdf.parse(txtInfo[3]);

            String fStart = sdf.format(date1);
            String fEnd = sdf.format(date2);
            //////////////////////////////////

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");

            start = LocalDateTime.parse(fStart, dtf);
            end = LocalDateTime.parse(fEnd, dtf);
            events.add(i, new EventImpl(txtInfo[0], txtInfo[1], start, end, txtInfo[4], txtInfo[5]));
        }
    }

    public static void loadMovies(ArrayList<String[]> data, Context c){

        for (int i = 0; i < data.size(); i++) {
            String[] txtInfo = data.get(i);
            String imgSrc = txtInfo[3].toLowerCase();
            String imgSrcName = imgSrc.substring(0, imgSrc.indexOf('.'));
            int imgRes = c.getResources().getIdentifier(imgSrcName, "drawable", c.getPackageName());
            movies.add(i, new MovieImpl(txtInfo[0], txtInfo[1], txtInfo[2], imgSrc, imgRes));
        }
    }
}
