package com.assignment1.model;

import java.util.ArrayList;

public interface Event {

    public String getID();

    public String getTitle();

    public void setTitle(String t);

    public String getVenue();

    public void setVenue(String v);

    public String getStartDate();

    public void setStartDate(String s);

    public String getEndDate();

    public void setEndDate(String e);

    public String getLocation();

    public void setLocation(String l);

    public int getNumAttendees();

    public ArrayList<String> getAttendees();

    public void setAttendees(ArrayList<String> a);
}
