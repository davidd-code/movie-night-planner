package com.example.assignment_1.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_1.R;

public class NotificationThresholdDialog extends AppCompatDialogFragment {

    EditText notificationThreshold;
    int notificationThresholdInt = 60;
    Button remind, dismiss, cancel;

    private NotificationOnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_notification_threshold, null);

        notificationThreshold = view.findViewById(R.id.notification_editText);
        remind = view.findViewById(R.id.remind_button);
        dismiss = view.findViewById(R.id.dismiss_button);
        cancel = view.findViewById(R.id.cancel_button);

        builder.setView(view).setTitle("Notification Threshold").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String toast = "Int " + notificationThreshold.getText();
                Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
                notificationThresholdInt = Integer.parseInt(notificationThreshold.getText().toString());
            }
        });

        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onRemindClick();
                }
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onDismissClick();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onCancelClick();
                }
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface NotificationOnClickListener {
        void onRemindClick();
        void onDismissClick();
        void onCancelClick();
    }


}

