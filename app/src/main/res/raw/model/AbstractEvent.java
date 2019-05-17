package com.assignment1.model;

import java.util.ArrayList;

public abstract class AbstractEvent implements com.assignment1.model.Event {

    protected String eventID;
    protected String eventTitle;
    protected String startDate;
    protected String endDate;
    protected String venue;
    protected String location;
    protected ArrayList<String> attendees;

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

    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String s){
        this.startDate = s;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public void setEndDate(String e){
        this.endDate = e;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLocation(String l){
        this.location = l;
    }

    public int getNumAttendees(){ return this.attendees.size(); }

    public ArrayList<String> getAttendees(){
        return this.attendees;
    }

    public void setAttendees(ArrayList<String> a){
        this.attendees = a;
    }
}
