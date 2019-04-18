package me.daviddoan.planner.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.daviddoan.planner.R;

public class ContactRecyclerListAdapter extends RecyclerView.Adapter<ContactRecyclerListAdapter.RecyclerViewHolder>{
    private ArrayList<String[]> mContactList;
    private ContactRecyclerListListener mContactRecyclerListListener;

    public ContactRecyclerListAdapter(ArrayList<String[]> contactList, ContactRecyclerListListener listener) {
        this.mContactList = contactList;
        this.mContactRecyclerListListener = listener;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        ContactRecyclerListListener contactRecyclerListListener;

        public RecyclerViewHolder(@NonNull View itemView, ContactRecyclerListListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.addContactBtn);
            mTextView1 = itemView.findViewById(R.id.contactNameTextView);
            mTextView2 = itemView.findViewById(R.id.contactNumberTextView);
            this.contactRecyclerListListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            contactRecyclerListListener.onContactClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v, mContactRecyclerListListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        String[] currentItem = mContactList.get(i);
        recyclerViewHolder.mTextView1.setText(currentItem[0]);
        recyclerViewHolder.mTextView2.setText(currentItem[1]);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

//    @Override
//    public int getItemCount() {
//        return mEventList.size();
//    }

    public interface ContactRecyclerListListener {
        void onContactClick(int position);
    }

}
