package com.example.shelton.practiceapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.GregorianCalendar;


public class CalendarFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener{

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

        reminder = (Button) getActivity().findViewById(R.id.b_reminder);
        reminder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.title_dialog_reminder);
        builder.setView(inflater.inflate(R.layout.dialog_set_reminder, null));
        builder.setNegativeButton("Cancel", this);
        builder.setPositiveButton("OK", this);

        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == DialogInterface.BUTTON_POSITIVE) {
            // To allow findById in dialog.
            Dialog dialog = (Dialog) dialogInterface;

            EditText desc = (EditText) dialog.findViewById(R.id.in_reminder);
            TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker_reminder);
            DatePicker datePicker = (DatePicker) getActivity().findViewById(R.id.datePicker_reminder);

            // Alarm time format.
            GregorianCalendar alarmTime = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());

            Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
            alarmIntent.putExtra("desc", desc.getText().toString());

            AlarmManager alarmManager = (AlarmManager) getActivity()
                    .getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(),
                    PendingIntent.getBroadcast(getActivity(), 1, alarmIntent,
                            PendingIntent.FLAG_ONE_SHOT));

            dialogInterface.dismiss();
        } else {
            dialogInterface.cancel();
        }

    }
}
