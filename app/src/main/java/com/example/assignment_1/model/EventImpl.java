package com.example.assignment_1.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.assignment_1.model.EventModel.events;
import static com.example.assignment_1.model.EventModel.movies;

public class EventImpl extends AbstractEvent implements Serializable {

    private MovieImpl chosenMovie;
    private ArrayList<Contact> attendees;

    public EventImpl (){
        this.eventID = Integer.toString(events.size());
        attendees = new ArrayList<>();
    }

    public EventImpl(String id, String t, LocalDateTime s, LocalDateTime e, String v, String l){

        this.eventID = id;
        this.eventTitle = t;
        this.startDate = s;
        this.endDate = e;
        this.venue = v;
        this.location = l;
        attendees = new ArrayList<>();
    }

    public EventImpl(String id, String t, String s, String e, String v, String l, String movieID){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        this.eventID = id;
        this.eventTitle = t;
        this.startDate = LocalDateTime.parse(s, dtf);
        this.endDate = LocalDateTime.parse(e, dtf);
        this.venue = v;
        this.location = l;
        if(movieID != null)
            this.chosenMovie = movies.get(Integer.parseInt(movieID) - 1);
        attendees = new ArrayList<>();

    }

    public String ldtToString(LocalDateTime ldt) {
        String dateTimeString;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        dateTimeString = ldt.format(dtf);
        return dateTimeString;
    }

    public boolean isAttending(Contact contact) {
        boolean status = false;
        for (Contact attendee : attendees) {
            if ( attendee.getPhone().equals(contact.getPhone()) ) {
                return true;
            }
        }
        return false;
    }
    public void setChosenMovie(MovieImpl movie) {
        this.chosenMovie = movie;
    }

    public MovieImpl getChosenMovie() {
        return this.chosenMovie;
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
    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDateTime s){
        this.startDate = s;
    }

    @Override
    public LocalDateTime getEndDate(){
        return this.endDate;
    }

    @Override
    public void setEndDate(LocalDateTime e){
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
            return this.attendees.size();
    }

    @Override
    public ArrayList<Contact> getAttendees(){
        return this.attendees;
    }
    
    @Override
    public void addAttendees(Contact c){
        this.attendees.add(c);
    }

    public void removeAttendee(Contact c){
        this.attendees.remove(c);
    }

    public String toString(){

        String eventInfo = "eventInfo toString: ID= " + this.getID() + " Title= " + this.getTitle() + " StartDate= " + this.getStartDate() + " EndDate= " + this.getEndDate() + " Venue= " + this.getVenue() + " Location= " + this.getLocation();
        return eventInfo;
    }
}
