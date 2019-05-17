package com.assignment1.model;

import java.util.ArrayList;

public class EventImpl extends AbstractEvent {

    public EventImpl(String id, String t, String s, String e, String v, String l){

        this.eventID = id;
        this.eventTitle = t;
        this.startDate = s;
        this.endDate = e;
        this.venue = v;
        this.location = l;
    }

    public void changeTitle(String s){
        this.eventTitle = s;
    }

    @Override
    public String getID(){
        return this.eventID;
    }

    @Override
    public void setID(String id){
        this.eventID = id;
    }

    @Override
    public String getTitle(){
        return this.eventTitle;
    }

    @Override
    public void setTitle(String t){
        this.eventTitle = t;
    }

    @Override
    public String getVenue(){
        return this.venue;
    }

    @Override
    public void setVenue(String v){
        this.venue = v;
    }

    @Override
    public String getStartDate(){
        return this.startDate;
    }

    @Override
    public void setStartDate(String s){
        this.startDate = s;
    }

    @Override
    public String getEndDate(){
        return this.endDate;
    }

    @Override
    public void setEndDate(String e){
        this.endDate = e;
    }

    @Override
    public String getLocation(){
        return this.location;
    }

    @Override
    public void setLocation(String l){
        this.location = l;
    }

    @Override
    public int getNumAttendees(){
            return 0;
    }

    @Override
    public ArrayList<String> getAttendees(){
        return this.attendees;
    }

    @Override
    public void setAttendees(ArrayList<String> a){
        this.attendees = a;
    }

    public String toString(){

        String eventInfo = "eventInfo toString: ID= " + this.getID() + " Title= " + this.getTitle() + " StartDate= " + this.getStartDate() + " EndDate= " + this.getEndDate() + " Venue= " + this.getVenue() + " Location= " + this.getLocation();
        return eventInfo;
    }
}
