package com.example.assignment_1.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Event {

    public String getID();

    public String getTitle();

    public void setTitle(String t);

    public String getVenue();

    public void setVenue(String v);

    public LocalDateTime getStartDate();

    public void setStartDate(LocalDateTime s);

    public LocalDateTime getEndDate();

    public void setEndDate(LocalDateTime e);

    public String getLocation();

    public void setLocation(String l);

    public int getNumAttendees();

    public ArrayList<Contact> getAttendees();

    public void addAttendees(Contact c);
}
