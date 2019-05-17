package com.example.assignment_1.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.assignment_1.R;

public class GetLocationDialog extends AppCompatDialogFragment {

    private EditText lat_Text, long_Text;
    private LocationDialogListener ldListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_location_layout, null);

        builder.setView(view);
        builder.setTitle("Location");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String lat = lat_Text.getText().toString();
                String lon = long_Text.getText().toString();

                String location = lat + ", " + lon;
                ldListener.applyTexts(location);
            }
        });

        lat_Text = view.findViewById(R.id.location_lat);
        long_Text = view.findViewById(R.id.location_long);

        lat_Text.setText(getArguments().getString("latitude"));
        long_Text.setText(getArguments().getString("longitude"));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ldListener = (LocationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + ":::::>>>>>> inner interface listemer");
        }
    }

    public interface LocationDialogListener{
        void applyTexts(String location);
    }

}
