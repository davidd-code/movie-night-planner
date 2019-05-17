package com.example.assignment_1.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AbstractEvent implements Event {

    protected String eventID;
    protected String eventTitle;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected String venue;
    protected String location;
    protected ArrayList<Contact> attendees;

    public AbstractEvent(){}
    public AbstractEvent(String id, String t, String s, String e, String v, String l){}

    public String getID(){
        return this.eventID;
    }

    public void setID(String id){
        this.eventID = id;
    }

    public String getTitle(){
        return this.eventTitle;
    }

    public void setTitle(String t){
        this.eventTitle = t;
    }

    public String getVenue(){
        return this.venue;
    }

    public void setVenue(String v){
        this.venue = v;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public void setStartDate(LocalDateTime s){
        this.startDate = s;
    }

    public LocalDateTime getEndDate(){
        return this.endDate;
    }

    public void setEndDate(LocalDateTime e){
        this.endDate = e;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLocation(String l){
        this.location = l;
    }

    public int getNumAttendees(){ return this.attendees.size(); }

    public ArrayList<Contact> getAttendees(){
        return this.attendees;
    }

    public void addAttendees(Contact c){
        this.attendees.add(c);
    }
}
