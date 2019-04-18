package me.daviddoan.planner.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import me.daviddoan.planner.R;
import me.daviddoan.planner.adapter.ContactRecyclerListAdapter;
import me.daviddoan.planner.model.EventImpl;
import me.daviddoan.planner.model.EventModel;
import me.daviddoan.planner.viewmodel.AppViewModel;

public class ViewContactsActivity extends AppCompatActivity implements ContactRecyclerListAdapter.ContactRecyclerListListener {
    public static final int ADD_CONTACT = 333;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppViewModel mAppViewModel;
    private EventImpl currentEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        // Set up ViewModel object
        mAppViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        mAppViewModel.init();

        currentEvent = EventModel.getInstance().getEventFromId(getIntent().getStringExtra("currentEventId"));
        currentEvent.setContactRecyclerViewAdapter(this);
        mRecyclerView = findViewById(R.id.contactsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ArrayList<String[]> attendees = new ArrayList<>();
        mAdapter = currentEvent.getmContactAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Button edit_addContactsBtn = (Button) findViewById(R.id.edit_addContactsBtn);
        edit_addContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, ADD_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case ADD_CONTACT:
                if(resultCode == Activity.RESULT_OK) {
                    Context context = getApplicationContext();
                    CharSequence text;
                    int duration = Toast.LENGTH_SHORT;
                    String[] newContact = new String[2];
//                    Works for phone / name only
                    Uri contactUri = data.getData();
                    String[] nameProjection = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
                    Cursor nameCursor = getApplicationContext().getContentResolver().query(contactUri, nameProjection,
                            null, null, null);
                    String name="";
//                     If the cursor returned is valid, get the phone number
                    if(nameCursor != null && nameCursor.moveToFirst()) {
                        name = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        newContact[0] = name;
                    }
                    String[] phoneProjection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor phoneCursor = getApplicationContext().getContentResolver().query(contactUri, phoneProjection,
                            null, null, null);
                    if(phoneCursor != null && phoneCursor.moveToFirst()) {
                        int phoneIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNo = phoneCursor.getString(phoneIndex);
                        newContact[1] = phoneNo;
                        // If contact is not already invited to this event
//                        if(!EventModel.getInstance().getContactsFromId(getIntent().getExtras().getString("ID")).contains(newContact)) {
                        if(!currentEvent.checkAttendee(phoneNo)) {
                            currentEvent.addAttendee(newContact[0], newContact[1]);
                            mAdapter.notifyDataSetChanged();
                            text = name + " has been added to this event";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } else {
                            text = name + " has already been invited to this event";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }


                }
        }
    }

    @Override
    public void onContactClick(int position) {

    }
}
