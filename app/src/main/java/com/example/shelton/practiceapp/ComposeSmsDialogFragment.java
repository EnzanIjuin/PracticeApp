package com.example.shelton.practiceapp;


import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class ComposeSmsDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static String TAG = "RAWR sms dialog";
    private final String[] COLUMNS = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                      ContactsContract.CommonDataKinds.Phone.NUMBER};

    private EditText msg, pNumber;
    private Spinner spinContacts;
    private Cursor contacts;
    private Button send, cancel;

    public ComposeSmsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compose_sms_dialog, container, false);

        getDialog().setTitle("Compose");

        // Contacts cursor
        contacts = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, COLUMNS[0] + " ASC");

        // Setup spinner
        spinContacts = (Spinner) view.findViewById(R.id.spinner_contacts);
        spinContacts.setOnItemSelectedListener(this);
        spinContacts.setAdapter(new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item, contacts, new String[] {COLUMNS[0]},
                new int[] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

        // Other views
        msg = (EditText) view.findViewById(R.id.in_msg);
        pNumber = (EditText) view.findViewById(R.id.in_pnumber);
        send = (Button) view.findViewById(R.id.b_send);
        cancel = (Button) view.findViewById(R.id.b_cancel);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contacts.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        contacts.moveToPosition(i);
        pNumber.setText(contacts.getString(contacts.getColumnIndex(COLUMNS[1])));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void sendSMS(CharSequence pNumber, CharSequence msg) {
        SmsManager manager = SmsManager.getDefault();

        List<String> messages = manager.divideMessage(msg.toString());
        for(String message : messages) {
            try {
                manager.sendTextMessage(pNumber.toString(), null, message, null, null);
            } catch(Exception e) {
                Toast.makeText(getActivity(), "Failed to send text!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_send:
                sendSMS(pNumber.getText(), msg.getText());
                getDialog().dismiss();
                Log.d(TAG, "Message sent!");
                Toast.makeText(getActivity(), "Message sent!", Toast.LENGTH_SHORT).show();
                break;
            default:
                getDialog().cancel();
                break;
        }
    }
}
