package com.example.assignment_1.view;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.assignment_1.R;
import com.example.assignment_1.controller.ContactItemClickListener;
import com.example.assignment_1.data.DatabaseHelper;
import com.example.assignment_1.model.Contact;
import com.example.assignment_1.model.EventImpl;
import com.example.assignment_1.viewModel.ContactListAdapter;

import java.util.ArrayList;

import static com.example.assignment_1.model.EventModel.contactAdapter;
import static com.example.assignment_1.model.EventModel.contacts;


public class ContactListFragment extends Fragment {

    private View view;
    private RecyclerView clf_RecyclerView;
    private ContactListAdapter clf_Adapter;
    private RecyclerView.LayoutManager clf_LayoutManager;

    private Button inviteButton;
    private EventImpl currentEvent;
    private DatabaseHelper dbHelper;


    public ContactListFragment() {
    }

    public static ContactListFragment newInstance(EventImpl currentEvent) {
        ContactListFragment contactListFragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putSerializable("current_event", currentEvent);
        contactListFragment.setArguments(args);
        return contactListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dbHelper = DatabaseHelper.getHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(dbHelper.contactsTableEmpty()) {
                    //System.out.println("NOT FROM DATABASE");
                    contacts = loadContacts();
                    for (Contact contact : contacts) {
                        dbHelper.addContact(contact);
                    }
                }else{
                    //System.out.println("FROM DATABASE");
                    dbHelper.readContactTable();
                }
            }
        }).start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_list, viewGroup, false);
        buildRecyclerView();
        return view;
    }

    public void buildRecyclerView() {
        clf_RecyclerView = view.findViewById(R.id.contact_RecyclerView);
        clf_LayoutManager = new LinearLayoutManager(getContext());
        clf_RecyclerView.setLayoutManager(clf_LayoutManager);
        currentEvent = (EventImpl) getArguments().getSerializable("current_event");
        clf_Adapter = new ContactListAdapter(currentEvent);
        contactAdapter = clf_Adapter;
        clf_RecyclerView.setAdapter(clf_Adapter);
        clf_Adapter.setOnItemClickListener(new ContactItemClickListener(getContext(), currentEvent));

        inviteButton = view.findViewById(R.id.invite_contact_button);
        inviteButton.setOnClickListener(new ContactItemClickListener(getContext(), currentEvent));
    }

    public ArrayList<Contact> loadContacts() {

        String id, name, phone, email;
        ContentResolver cr = getContext().getContentResolver();

        Cursor cursor1 = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        if (cursor1.getCount() > 0) {
            while (cursor1.moveToNext()) {
                id = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                int hasPhoneNumber = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                            new String[]{id},
                            null);

                    while (cursor2.moveToNext()) {
                        phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Cursor cursor3 = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " =?",
                                new String[]{id},
                                null);

                        while (cursor3.moveToNext()) {
                            email = cursor3.getString(cursor3.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            contacts.add(new Contact(id, name, phone, email));
                        }
                        cursor3.close();
                    }
                    cursor2.close();
                }
            }
            cursor1.close();
        }
        return contacts;
    }

}
