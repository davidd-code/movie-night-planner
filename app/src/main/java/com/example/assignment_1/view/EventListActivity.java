package com.example.assignment_1.view;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.AddEventOnClickListener;
import com.example.assignment_1.controller.EditEventOnClickListener;
import com.example.assignment_1.controller.MapOnClickListener;
import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.model.CustomComparator;
import com.example.assignment_1.model.EventModel;
import com.example.assignment_1.model.FileLoader;
import com.example.assignment_1.restapi.HttpURLConnectionAsyncTask;
import com.example.assignment_1.restapi.LocationServiceReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;

import static com.example.assignment_1.model.EventModel.eventAdapter;
import static com.example.assignment_1.model.EventModel.events;

public class EventListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    public static final String DATABASE_UPDATED_ACTION = "com.example.assignment_1.DB_UPDATED";
    public static final String UPDATE_ACTION_TEXT = "com.example.assignment_1.UPDATE_TEXT";
    public static final Intent update = new Intent(DATABASE_UPDATED_ACTION);
    public static final int HOUR_IN_MILLISECONDS = 3600000;
    private FileLoader fl = new FileLoader();

    private RecyclerView eRecyclerView;
    private RecyclerView.LayoutManager eLayoutManager;
    private Toolbar toolbar;
    private ImageView addEventButton;

    private static final String TAG="EventListActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    int threshold, period, remindAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        toolbar = findViewById(R.id.event_list_toolbar);
        setSupportActionBar(toolbar);



        buildRecyclerView();
        setButtons();
        isServicesOK();

        dbHelper = DatabaseHelper.getHelper(this);
        dbHelper.open();
        dbHelper.close();

        threshold = HOUR_IN_MILLISECONDS;
        setAlarm(threshold);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        eventAdapter.notifyDataSetChanged();
    }

    public final void sendBroadcast(View view) {
        update.putExtra(UPDATE_ACTION_TEXT, "UI update available");
        sendBroadcast(update);
    }

    private BroadcastReceiver uiUpdater = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DATABASE_UPDATED_ACTION.equals(intent.getAction())) {
                String updateAvailable = intent.getStringExtra(UPDATE_ACTION_TEXT);
                Toast.makeText(context, "UI updated", Toast.LENGTH_SHORT).show();
                eventAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DATABASE_UPDATED_ACTION);
        registerReceiver(uiUpdater, filter);
        events.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper.syncDatabase();
                sendBroadcast(update);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //eventAdapter.setOnItemClickListener(null);
        unregisterReceiver(uiUpdater);
    }
    public void isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(EventListActivity.this);

        if(available == ConnectionResult.SUCCESS) {
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play services is working");
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(EventListActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAlarm(int milliseconds) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, LocationServiceReceiver.class);
        intent.putExtra("notificationPeriod", milliseconds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 1, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CustomComparator c = new CustomComparator();
        switch(item.getItemId()){
            case R.id.notification_threshold:
                NotificationOptionsDialog notificationDialog = new NotificationOptionsDialog();
                notificationDialog.show(getSupportFragmentManager(), "Notification Threshold");

                break;
            case R.id.sort_ascending:
                c.sortAscending();
                Toast.makeText(this, "Ascending", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_descending:
                c.sortDescending();
                Toast.makeText(this, "Descending", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setButtons() {
        addEventButton = findViewById(R.id.addEventButton);
        ImageView mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new MapOnClickListener(this));
        addEventButton.setOnClickListener(new AddEventOnClickListener(this, eventAdapter));
    }

    public void buildRecyclerView(){
        eRecyclerView = findViewById(R.id.eRecyclerView);
        eRecyclerView.setHasFixedSize(true);
        eLayoutManager = new LinearLayoutManager(this);

        eRecyclerView.setLayoutManager(eLayoutManager);
        eRecyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener(new EditEventOnClickListener(this, eventAdapter));
    }



}
