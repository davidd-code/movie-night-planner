package com.assignment1.model;

import java.util.ArrayList;

public final class EventModel {

    private EventModel(){};

    public static ArrayList<com.assignment1.model.EventImpl> events = new ArrayList<com.assignment1.model.EventImpl>();
    public static ArrayList<MovieImpl> movies = new ArrayList<MovieImpl>();

    public static void loadEvents(ArrayList<String[]> data){

        for (int i = 0; i < data.size(); i++) {
            String[] txtInfo = data.get(i);
            events.add(new com.assignment1.model.EventImpl(txtInfo[0], txtInfo[1], txtInfo[2], txtInfo[3], txtInfo[4], txtInfo[5]));

            System.out.println(events.get(i).toString());
        }
    }

    public static void loadMovies(ArrayList<String[]> data){

        for (int i = 0; i < data.size(); i++) {
            String[] txtInfo = data.get(i);
            movies.add(new MovieImpl(txtInfo[0], txtInfo[1], txtInfo[2], txtInfo[3]));
        }
    }

}
