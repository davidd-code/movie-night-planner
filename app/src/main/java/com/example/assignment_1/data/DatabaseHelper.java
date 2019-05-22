package com.example.assignment_1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.model.EventModel;
import com.example.assignment_1.model.FileLoader;
import com.example.assignment_1.model.MovieImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import static com.example.assignment_1.model.EventModel.events;
import static com.example.assignment_1.model.EventModel.movies;

public class DatabaseHelper extends SQLiteOpenHelper {

    private FileLoader fl = new FileLoader();
    private Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MovieEventsDB.db";

    public static final String TABLE_EVENTS = "Events";
    public static final String COL_EVENT_ID = "EventID";
    public static final String COL_EVENT_TITLE = "EventTitle";
    public static final String COL_EVENT_START = "StartDate";
    public static final String COL_EVENT_END = "EndDate";
    public static final String COL_EVENT_VENUE = "Venue";
    public static final String COL_EVENT_LOCATION = "Location";

    public static final String TABLE_MOVIES = "Movies";
    public static final String COL_MOVIE_ID = "MovieID";
    public static final String COL_MOVIE_TITLE = "MovieTitle";
    public static final String COL_MOVIE_YEAR = "Year";
    public static final String COL_MOVIE_POSTER = "Poster";

    public static final String TABLE_CONTACTS = "Contacts";
    public static final String COL_CONTACT_ID = "ContactID";
    public static final String COL_CONTACT_NAME = "Name";
    public static final String COL_CONTACT_PHONE = "Phone";
    public static final String COL_CONTACT_EMAIL = "Email";

    public static final String CREATE_EVENTS_TABLE =
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
    public static final String CREATE_MOVIES_TABLE =
            "CREATE TABLE " + TABLE_MOVIES + "(" +
                    COL_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_MOVIE_TITLE + " TEXT, " +
                    COL_MOVIE_YEAR + " TEXT, " +
                    COL_MOVIE_POSTER + " TEXT " + ");";
    public static final String CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COL_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_CONTACT_NAME + " TEXT, " +
                    COL_CONTACT_PHONE + " TEXT, " +
                    COL_CONTACT_EMAIL + " TEXT " + ");";

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

    public void loadTextFile(){
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

    public void populateNewDatabase(SQLiteDatabase db) {
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

            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] eventToString(EventImpl event) {
        String[] eventValue = new String[7];
        eventValue[0] = event.getID();
        eventValue[1] = event.getTitle();
        eventValue[2] = event.ldtToString(event.getStartDate());
        eventValue[3] = event.ldtToString(event.getEndDate());
        eventValue[4] = event.getVenue();
        eventValue[5] = event.getLocation();
        eventValue[6] = event.getChosenMovie().getID();

        return eventValue;
    }

    public void addEvent(EventImpl event, SQLiteDatabase db){
        try {
            String[] eventValue = eventToString(event);
            ContentValues values = new ContentValues();

            values.put(COL_EVENT_TITLE, eventValue[1]);
            values.put(COL_EVENT_START, eventValue[2]);
            values.put(COL_EVENT_END, eventValue[3]);
            values.put(COL_EVENT_VENUE, eventValue[4]);
            values.put(COL_EVENT_LOCATION, eventValue[5]);
            if(event.getChosenMovie() != null)
                values.put(COL_MOVIE_ID, eventValue[6]);

            db.insert(TABLE_EVENTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMovie(MovieImpl movie, SQLiteDatabase db) {
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

    public void deleteEvent(String eventID) {
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE " + COL_EVENT_ID + " =\"" + eventID + "\";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMovieTable(SQLiteDatabase db) {
        String mID, mTitle, year, poster;
        String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("MovieID")) != null) {
                mID = cursor.getString(cursor.getColumnIndex("MovieID"));
                mTitle = cursor.getString(cursor.getColumnIndex("MovieTitle"));
                year = cursor.getString(cursor.getColumnIndex("Year"));
                poster = cursor.getString(cursor.getColumnIndex("Poster"));

                String imgSrc = poster.substring(0, poster.indexOf('.'));
                int imgRes = context.getResources().getIdentifier(imgSrc, "drawable", context.getPackageName());
                movies.add(new MovieImpl(mID, mTitle, year, poster, imgRes));
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void readEventTable(SQLiteDatabase db) {
        String eID, eTitle, start, end, venue, location, mID;
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("EventID")) != null) {
                eID = cursor.getString(cursor.getColumnIndex("EventID"));
                eTitle = cursor.getString(cursor.getColumnIndex("EventTitle"));
                start = cursor.getString(cursor.getColumnIndex("StartDate"));
                end = cursor.getString(cursor.getColumnIndex("EndDate"));
                venue = cursor.getString(cursor.getColumnIndex("Venue"));
                location = cursor.getString(cursor.getColumnIndex("Location"));
                if(cursor.getString(cursor.getColumnIndex("MovieID")) != null)
                    mID = cursor.getString(cursor.getColumnIndex("MovieID"));
                else
                    mID = null;

                events.add(new EventImpl(eID, eTitle, start, end, venue, location, mID));
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void loadDatabase() {

        try{
            SQLiteDatabase db = getWritableDatabase();
            readMovieTable(db);
            readEventTable(db);
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean updateEvent(EventImpl event) {
        String[] eventValue = eventToString(event);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EVENT_ID, eventValue[0]);
        values.put(COL_EVENT_TITLE, eventValue[1]);
        values.put(COL_EVENT_START, eventValue[2]);
        values.put(COL_EVENT_END, eventValue[3]);
        values.put(COL_EVENT_VENUE, eventValue[4]);
        values.put(COL_EVENT_LOCATION, eventValue[5]);
        values.put(COL_MOVIE_ID, eventValue[6]);

        db.update(TABLE_EVENTS, values, "EventID = ?", new String[]{eventValue[0]});
        return true;
    }
}