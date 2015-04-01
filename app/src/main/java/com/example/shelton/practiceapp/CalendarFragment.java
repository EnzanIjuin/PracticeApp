package com.example.shelton.practiceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private DatePicker calendar;
    private Button reminder;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendar = (DatePicker) getActivity().findViewById(R.id.datePicker_reminder);

        reminder = (Button) getActivity().findViewById(R.id.b_reminder);
        reminder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.title_dialog_reminder);
        builder.setView(inflater.inflate(R.layout.dialog_set_reminder, null));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TimePicker picker = (TimePicker) getActivity().findViewById(R.id.timePicker_reminder);
                EditText desc = (EditText) getActivity().findViewById(R.id.in_reminder);


            }
        });

        builder.show();
    }
}
