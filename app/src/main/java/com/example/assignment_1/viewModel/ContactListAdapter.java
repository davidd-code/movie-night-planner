package com.example.assignment_1.viewModel;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_1.R;
import com.example.assignment_1.model.Contact;
import com.example.assignment_1.model.EventImpl;

import static com.example.assignment_1.model.EventModel.contacts;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {

    private OnItemClickListener listener;
    private EventImpl currentEvent;

    public ContactListAdapter(EventImpl event) {
        this.currentEvent = event;
    }

    public static class ContactListViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName, phoneNumber, email;
        private CardView contactCard;
        private EventImpl currentEvent;

        public ContactListViewHolder(@NonNull final View itemView, final OnItemClickListener listener, final EventImpl event) {
            super(itemView);
            this.currentEvent = event;
            contactName = itemView.findViewById(R.id.contact_Name);
            phoneNumber = itemView.findViewById(R.id.phone_Number);
            email = itemView.findViewById(R.id.email);
            contactCard = itemView.findViewById(R.id.contactCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        int event_index = listener.getEventIndex();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, event_index);
                            if (currentEvent.isAttending(contacts.get(position))) {
                                contactCard.setCardBackgroundColor(Color.DKGRAY);
                            } else {
                                contactCard.setCardBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, int event_index);
        int getEventIndex();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        ContactListViewHolder cvh = new ContactListViewHolder(v, listener, currentEvent);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactListViewHolder contactListViewHolder, int i) {
        Contact currentContact = contacts.get(i);
        contactListViewHolder.contactName.setText(currentContact.getFullName());
        contactListViewHolder.phoneNumber.setText(currentContact.getPhone());
        contactListViewHolder.email.setText(currentContact.getEmail());
        if(currentEvent.isAttending(currentContact)){
            contactListViewHolder.contactCard.setCardBackgroundColor(Color.DKGRAY);
        }else{
            contactListViewHolder.contactCard.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
