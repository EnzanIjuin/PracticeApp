package com.example.shelton.practiceapp;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MyListAdapter extends CursorAdapter implements View.OnClickListener {

    public static final String INBOX = "content://sms/inbox";
    private static final String TAG = "RAWR Adapter";
    private LayoutInflater inflater;

    public MyListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.row_messages, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Button del = (Button) view.findViewById(R.id.b_del);
        Button read = (Button) view.findViewById(R.id.b_read);

        del.setOnClickListener(this);
        read.setOnClickListener(this);

        TextView name = (TextView) view.findViewById(R.id.text_name);
        TextView msg = (TextView) view.findViewById(R.id.text_msg);
        TextView time = (TextView) view.findViewById(R.id.text_time);

        name.setText(cursor.getString(cursor.getColumnIndex("address")));
        msg.setText(cursor.getString(cursor.getColumnIndex("body")));
        time.setText(timeString(cursor.getLong(cursor.getColumnIndex("date"))));
    }

    private String timeString(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("K:mma E (M/d/yy)");
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTimeInMillis(millis);
        gCal.setTimeZone(TimeZone.getDefault());

        return formatter.format(gCal.getTime());
    }

    @Override
    public void onClick(View view) {
        ListView listView = (ListView) view.getParent().getParent();
        int pos = listView.getPositionForView(view);

        switch(view.getId()) {
            case R.id.b_del:
                Log.d(TAG, "Delete from pos " + pos + " pressed!");
                break;
            case R.id.b_read:
                Log.d(TAG, "Read from pos " + pos + " pressed!");
                break;
        }
    }


}
