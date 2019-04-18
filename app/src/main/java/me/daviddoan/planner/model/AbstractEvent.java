package me.daviddoan.planner.model;

import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.daviddoan.planner.adapter.ContactRecyclerListAdapter;
import me.daviddoan.planner.model.interfaces.Event;
import me.daviddoan.planner.model.interfaces.Movie;
import me.daviddoan.planner.view.ViewContactsActivity;

public abstract class AbstractEvent implements Event {

    private String id, title, venue, location;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private Movie movie;
    private ArrayList<String[]> attendees;
    private RecyclerView.Adapter mContactAdapter;

    public AbstractEvent(String id, String title, String startDate, String endDate, String venue,
                         String location, Movie movie) {
        this.id = id;
        this.title = title;
        setCalendarFromString(this.startDate, startDate);
        setCalendarFromString(this.endDate, endDate);
        this.venue = venue;
        this.location = location;
        this.movie = movie;
        attendees = new ArrayList<>();
    }

    public Calendar setCalendarFromString(Calendar calendar, String date) {
        String[] startDateStringArray = date.split(" ");
        String[] startDateArray = startDateStringArray[0].split("/");
        calendar.set(Calendar.YEAR, Integer.parseInt(startDateArray[2]));
        calendar.set(Calendar.MONTH, Integer.parseInt(startDateArray[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateArray[0]));
        String[] startTimeArray = startDateStringArray[1].split(":");
        if(startDateStringArray[2].equals("AM")) {
            calendar.set(Calendar.AM_PM, 0);
        } else {
            calendar.set(Calendar.AM_PM, 1);
        }
        calendar.set(Calendar.HOUR, Integer.parseInt(startTimeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(startTimeArray[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(startTimeArray[2]));

        return calendar;
    }

    public void addAttendee(String name, String number) {
        String[] newContact = new String[2];
        newContact[0] = name;
        newContact[1] = number;
        if(!attendees.contains(newContact)) {
            attendees.add(newContact);
            mContactAdapter.notifyDataSetChanged();
        }
    }

    public boolean checkAttendee(String number) {
        boolean check = false;
        for(String[] element: attendees) {
            if(number.equals(element[1])) {
                check = true;
            }
        }
        return check;
    }

    public ArrayList<String[]> getAttendees() {
        return this.attendees;
    }

    public void setContactRecyclerViewAdapter(ViewContactsActivity activity) {
        mContactAdapter = new ContactRecyclerListAdapter(getAttendees(), activity);
    }

    public RecyclerView.Adapter getmContactAdapter() {
        return this.mContactAdapter;
    }



    public void setAttendees(ArrayList<String[]> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getStartDateString() {
//        return this.startDate;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        format.setTimeZone(this.startDate.getTimeZone());
        return format.format(this.startDate.getTime());
    }

    @Override
    public void setStartDate(String startDate) {
        setCalendarFromString(this.startDate, startDate);
    }

    @Override
    public String getEndDateString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        format.setTimeZone(this.endDate.getTimeZone());
        return format.format(this.endDate.getTime());
    }

    @Override
    public void setEndDate(String endDate) {
        setCalendarFromString(this.endDate, endDate);
    }

    @Override
    public String getVenue() {
        return this.venue;
    }

    @Override
    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Movie getMovie() {
        return this.movie;
    }

    @Override
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
