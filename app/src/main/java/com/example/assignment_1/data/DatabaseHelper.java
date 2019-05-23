package com.example.assignment_1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment_1.R;
import com.example.assignment_1.model.Contact;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.model.EventModel;
import com.example.assignment_1.model.FileLoader;
import com.example.assignment_1.model.MovieImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

import static com.example.assignment_1.model.EventModel.contacts;
import static com.example.assignment_1.model.EventModel.events;
import static com.example.assignment_1.model.EventModel.movies;

public class DatabaseHelper extends SQLiteOpenHelper {

    private FileLoader fl = new FileLoader();
    private Context context;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MovieEventsDB.db";

    private static final String TABLE_EVENTS = "Events";
    private static final String COL_EVENT_ID = "EventID";
    private static final String COL_EVENT_TITLE = "EventTitle";
    private static final String COL_EVENT_START = "StartDate";
    private static final String COL_EVENT_END = "EndDate";
    private static final String COL_EVENT_VENUE = "Venue";
    private static final String COL_EVENT_LOCATION = "Location";

    private static final String TABLE_MOVIES = "Movies";
    private static final String COL_MOVIE_ID = "MovieID";
    private static final String COL_MOVIE_TITLE = "MovieTitle";
    private static final String COL_MOVIE_YEAR = "Year";
    private static final String COL_MOVIE_POSTER = "Poster";

    private static final String TABLE_ATTENDEES = "Attendees";
    private static final String TABLE_CONTACTS = "Contacts";
    private static final String COL_CONTACT_ID = "ContactID";
    private static final String COL_CONTACT_NAME = "Name";
    private static final String COL_CONTACT_PHONE = "Phone";
    private static final String COL_CONTACT_EMAIL = "Email";

    private static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE " + TABLE_EVENTS + "(" +
                    COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_EVENT_TITLE + " TEXT, " +
                    COL_EVENT_START + " DATE, " +
                    COL_EVENT_END + " DATE, " +
                    COL_EVENT_VENUE + " TEXT, " +
                    COL_EVENT_LOCATION + " TEXT, " +
                    COL_MOVIE_ID + " INTEGER," +
                    " FOREIGN KEY (" + COL_MOVIE_ID + ")" +
                    " REFERENCES " + TABLE_MOVIES + "(" + COL_MOVIE_ID + "));";
    private static final String CREATE_MOVIES_TABLE =
            "CREATE TABLE " + TABLE_MOVIES + "(" +
                    COL_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_MOVIE_TITLE + " TEXT, " +
                    COL_MOVIE_YEAR + " TEXT, " +
                    COL_MOVIE_POSTER + " TEXT " + ");";
    private static final String CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COL_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_CONTACT_NAME + " TEXT, " +
                    COL_CONTACT_PHONE + " TEXT, " +
                    COL_CONTACT_EMAIL + " TEXT " + ");";
    private static final String CREATE_ATTENDEES_TABLE =
            "CREATE TABLE " + TABLE_ATTENDEES + "(" +
                    COL_EVENT_ID + " INTEGER, " +
                    COL_CONTACT_ID + " INTEGER, " +
                    " FOREIGN KEY (" + COL_EVENT_ID + ")" +
                    " REFERENCES " + TABLE_EVENTS + "(" + COL_EVENT_ID + "), " +
                    " FOREIGN KEY (" + COL_CONTACT_ID + ")" +
                    " REFERENCES " + TABLE_CONTACTS + "(" + COL_CONTACT_ID + "));";

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private void loadTextFile(){
        InputStream isEvents = context.getResources().openRawResource(R.raw.events);
        InputStream isMovies = context.getResources().openRawResource(R.raw.movies);
        BufferedReader brEvents = new BufferedReader(new InputStreamReader(isEvents));
        BufferedReader brMovies = new BufferedReader(new InputStreamReader(isMovies));

        try{
            EventModel.loadEvents(fl.loadFile(brEvents));
            EventModel.loadMovies(fl.loadFile(brMovies), context);
        }catch(ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                brEvents.close();
                brMovies.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void open() {
        getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_key=ON;");
        }
    }

    private void populateNewDatabase(SQLiteDatabase db) {
        for(MovieImpl movie : movies)
            addMovie(movie, db);
        for(EventImpl event : events)
            addEvent(event, db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MOVIES_TABLE);
            db.execSQL(CREATE_EVENTS_TABLE);
            db.execSQL(CREATE_CONTACTS_TABLE);
            db.execSQL(CREATE_ATTENDEES_TABLE);
            loadTextFile();
            populateNewDatabase(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDEES);

            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean contactsTableEmpty() {
        SQLiteDatabase db = getWritableDatabase();
        String count_query = "SELECT count(*) FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(count_query, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();
        close();
        if (count > 0)
            return false;
        else
            return true;
    }

    public void addEvent(EventImpl event, SQLiteDatabase db){
        try {
            ContentValues values = new ContentValues();

            values.put(COL_EVENT_TITLE, event.getTitle());
            values.put(COL_EVENT_START, event.ldtToString(event.getStartDate()));
            values.put(COL_EVENT_END, event.ldtToString(event.getEndDate()));
            values.put(COL_EVENT_VENUE, event.getVenue());
            values.put(COL_EVENT_LOCATION, event.getLocation());
            if(event.getChosenMovie() != null)
                values.put(COL_MOVIE_ID, event.getChosenMovie().getID());

            db.insert(TABLE_EVENTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMovie(MovieImpl movie, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();

            values.put(COL_MOVIE_TITLE, movie.getTitle());
            values.put(COL_MOVIE_YEAR, movie.getYear());
            values.put(COL_MOVIE_POSTER, movie.getPoster());

            db.insert(TABLE_MOVIES, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put(COL_CONTACT_ID, contact.getID());
            values.put(COL_CONTACT_NAME, contact.getFullName());
            values.put(COL_CONTACT_PHONE, contact.getPhone());
            values.put(COL_CONTACT_EMAIL, contact.getEmail());

            db.insert(TABLE_CONTACTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public void addAttendee(EventImpl event, Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put(COL_EVENT_ID, event.getID());
            values.put(COL_CONTACT_ID, contact.getID());

            db.insert(TABLE_ATTENDEES, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private void readMovieTable() {
        SQLiteDatabase db = getWritableDatabase();

        String mID, mTitle, year, poster;
        String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        movies.clear();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID)) != null) {
                mID = cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID));
                mTitle = cursor.getString(cursor.getColumnIndex(COL_MOVIE_TITLE));
                year = cursor.getString(cursor.getColumnIndex(COL_MOVIE_YEAR));
                poster = cursor.getString(cursor.getColumnIndex(COL_MOVIE_POSTER));

                String srcName = poster.toLowerCase();
                String imgSrc = srcName.substring(0, srcName.indexOf('.'));
                int imgRes = context.getResources().getIdentifier(imgSrc, "drawable", context.getPackageName());
                movies.add(new MovieImpl(mID, mTitle, year, poster, imgRes));
            }
            cursor.moveToNext();
        }
        for (int i=0; i<movies.size(); i++) {
            MovieImpl movie = movies.get(i);
            System.out.println("INDEX: " + i + " >>> " + "ID:" + movie.getID() + ", " + movie.getTitle());
        }
        cursor.close();
        close();
    }

    private void readEventTable() {
        SQLiteDatabase db = getWritableDatabase();

        String eID, eTitle, start, end, venue, location, mID;
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        events.clear();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_EVENT_ID)) != null) {
                eID = cursor.getString(cursor.getColumnIndex(COL_EVENT_ID));
                eTitle = cursor.getString(cursor.getColumnIndex(COL_EVENT_TITLE));
                start = cursor.getString(cursor.getColumnIndex(COL_EVENT_START));
                end = cursor.getString(cursor.getColumnIndex(COL_EVENT_END));
                venue = cursor.getString(cursor.getColumnIndex(COL_EVENT_VENUE));
                location = cursor.getString(cursor.getColumnIndex(COL_EVENT_LOCATION));
                mID = cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID));
                events.add(new EventImpl(eID, eTitle, start, end, venue, location, mID));
            }
            cursor.moveToNext();
        }
        for (int i=0; i<events.size(); i++) {
            EventImpl event = events.get(i);
            System.out.println("INDEX: " + i + " >>> " + "ID:" + event.getID() + ", " + event.getTitle());
        }
        cursor.close();
        close();
    }

    public void readContactTable() {
        SQLiteDatabase db = getWritableDatabase();

        String cID, name, phone, email;
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        contacts.clear();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_CONTACT_ID)) != null) {
                cID = cursor.getString(cursor.getColumnIndex(COL_CONTACT_ID));
                name = cursor.getString(cursor.getColumnIndex(COL_CONTACT_NAME));
                phone = cursor.getString(cursor.getColumnIndex(COL_CONTACT_PHONE));
                email = cursor.getString(cursor.getColumnIndex(COL_CONTACT_EMAIL));

                contacts.add(new Contact(cID, name, phone, email));
            }
            cursor.moveToNext();
        }
        for (int i=0; i<contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.println("INDEX: " + i + " >>> " + "ID:" + contact.getID() + ", " + contact.getFullName());
        }
        cursor.close();
        close();
    }

    private void readAttendees(EventImpl event) {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Contact> attendees = event.getAttendees();
        String eID, cID, query;
        String eventID = event.getID();

        query = "SELECT * FROM " + TABLE_ATTENDEES + " WHERE " + COL_EVENT_ID + "=" + eventID;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        attendees.clear();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_EVENT_ID)) != null) {
                eID = cursor.getString(cursor.getColumnIndex(COL_EVENT_ID));
                cID = cursor.getString(cursor.getColumnIndex(COL_CONTACT_ID));

                System.out.println("read attendees\n eID = " + eID);
                System.out.println("cID = " + cID);
                for (Contact contact : contacts) {
                    System.out.println("contact ID=" + contact.getID() + " " + contact.getFullName());
                    if (contact.getID().equals(cID)){
                        System.out.println("ADDED");
                        attendees.add(contact);
                    }
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
    }

    public void syncDatabase() {
        try {
            readMovieTable();
            readEventTable();
            readContactTable();
            for(EventImpl event : events){
                readAttendees(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean updateEvent(EventImpl event) {

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_EVENT_ID, event.getID());
            values.put(COL_EVENT_TITLE, event.getTitle());
            values.put(COL_EVENT_START, event.ldtToString(event.getStartDate()));
            values.put(COL_EVENT_END, event.ldtToString(event.getEndDate()));
            values.put(COL_EVENT_VENUE, event.getVenue());
            values.put(COL_EVENT_LOCATION, event.getLocation());
            if(event.getChosenMovie() != null)
                values.put(COL_MOVIE_ID, event.getChosenMovie().getID());

            db.update(TABLE_EVENTS, values, COL_EVENT_ID + "= ?", new String[]{event.getID()});
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }finally {
            close();
        }
        return true;
    }

    public void deleteEvent(String eventID) {
        System.out.println(eventID);
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_EVENTS, COL_EVENT_ID + "= ?", new String[]{eventID});
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public void removeAttendee(EventImpl event, Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_ATTENDEES, COL_EVENT_ID + "= ? AND " + COL_CONTACT_ID + "= ?", new String[]{event.getID(), contact.getID()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
}