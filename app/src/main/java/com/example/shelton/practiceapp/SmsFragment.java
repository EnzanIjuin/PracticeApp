package com.example.shelton.practiceapp;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class SmsFragment extends Fragment {

    private ListView smsListView;
    private MyListAdapter adapter;

    public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor c = getActivity().getContentResolver().query(Uri.parse(MyListAdapter.INBOX), null,
                null, null, null);

//        int i = 0;
//        while(c.moveToNext()) {
//            i++;
//            Log.d("RAWR SMS", "address: " + c.getString(c.getColumnIndex("address")));
//            Log.d("RAWR SMS", "body: " + c.getString(c.getColumnIndex("body")));
//        }
//        c.close();

        smsListView = (ListView) getActivity().findViewById(R.id.list_sms);
        adapter = new MyListAdapter(getActivity(), c, true);
        smsListView.setAdapter(adapter);
    }
}
