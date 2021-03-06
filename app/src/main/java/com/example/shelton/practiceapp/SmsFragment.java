package com.example.shelton.practiceapp;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Locale;


public class SmsFragment extends Fragment implements SmsListAdapter.Communicator,
        View.OnClickListener {

    private static final String TAG = "RAWR sms";

    private ListView smsListView;
    private Button show, compose;

    private SmsListAdapter adapter;
    private TextToSpeech tts;
    private Cursor c;

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

        c = getActivity().getContentResolver().query(Uri.parse(SmsListAdapter.INBOX), null,
                null, null, null);

        show = (Button) getActivity().findViewById(R.id.b_show_sms);
        compose = (Button) getActivity().findViewById(R.id.b_compose);
        show.setOnClickListener(this);
        compose.setOnClickListener(this);

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.US);
            }
        });

        smsListView = (ListView) getActivity().findViewById(R.id.list_sms);
        adapter = new SmsListAdapter(getActivity(), c, true, this);
        smsListView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(c != null) c.close();
        if(tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void delete(int pos) {
        c.moveToPosition(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete SMS?");
        builder.setMessage("Are you sure you want to delete this SMS?\n\n"
                + "From: " + c.getString(c.getColumnIndex("address")) + "\n"
                + c.getString(c.getColumnIndex("body")));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Deleting SMS id " + c.getString(0) + " from "
                        + c.getString(c.getColumnIndex("address")));

                getActivity().getContentResolver().delete(Uri.parse("content://sms/" + c.getString(0)),
                        null, null);

                adapter.notifyDataSetChanged();

                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(true);

        builder.show();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void read(int pos) {
        c.moveToPosition(pos);
        if(tts != null)
            tts.speak(c.getString(c.getColumnIndex("body")), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_compose:
                composeDialog();
                break;
            case R.id.b_show_sms:
                break;
        }
    }

    private void composeDialog() {

        ComposeSmsDialogFragment dialogFragment = new ComposeSmsDialogFragment();
        FragmentManager manager = getFragmentManager();

        dialogFragment.show(manager, "sms");

    }

}
