package com.example.assignment_1.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.assignment_1.R;
import com.example.assignment_1.model.EventImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

import static com.example.assignment_1.model.EventModel.events;

public class DateTimePickOnClickListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EventImpl currentEvent;
    private int eventIndex;
    private Context context;

    private TextView startDate, endDate;
    private ImageView endDateButton;

    private int year, month, day;
    private int hour, minute;
    private boolean startDateSelected = false;

    private Calendar cal = Calendar.getInstance();
    private Calendar[] date = new Calendar[2];

    private int index = 0;

    public DateTimePickOnClickListener(Context c, int i, EventImpl currentEvent) {
        this.context = c;
        this.eventIndex = i;
        this.currentEvent = currentEvent;
    }

    @Override
    public void onClick(View v) {
        pickDateTime(v);

        startDate = ((Activity) context).findViewById(R.id.startDate);
        endDate = ((Activity) context).findViewById(R.id.endDate);
        endDateButton = ((Activity) context).findViewById(R.id.endDateButton);
    }

    public void pickDateTime(View v) {

        DatePickerDialog datePicker;
        Calendar cal = Calendar.getInstance();
        date[0] = Calendar.getInstance();
        date[1] = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        datePicker = new DatePickerDialog(context, this, year, month, day);
        datePicker.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (startDateSelected) {
            index = 1;
        }

        date[index].set(Calendar.YEAR, year);
        date[index].set(Calendar.MONTH, month);
        date[index].set(Calendar.DAY_OF_MONTH, dayOfMonth);

        TimePickerDialog timePicker = new TimePickerDialog(context, this, hour, minute, false);
        timePicker.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        date[index].set(Calendar.HOUR_OF_DAY, hourOfDay);
        date[index].set(Calendar.MINUTE, minute);

        LocalDateTime dateTime = toLocalDateTime(cal);


        if (!startDateSelected) {
            currentEvent.setStartDate(dateTime);
            startDate.setText(currentEvent.ldtToString(dateTime));
            endDateButton.setEnabled(true);
            startDateSelected = true;
        } else if (validEndDate(date[0], date[1])) {
            currentEvent.setEndDate(dateTime);
            endDate.setText(currentEvent.ldtToString(dateTime));
        } else {
            System.out.println("::::::::::::ERROR INVALID DATES:::::::::::");
        }
    }

    public LocalDateTime toLocalDateTime(Calendar cal) {
        if (cal == null) {
            return null;
        }
        TimeZone tz = cal.getTimeZone();
        ZoneId zID = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(cal.toInstant(), zID);
    }

    public boolean validEndDate(Calendar startDate, Calendar endDate) {
        if (startDate.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }
}
