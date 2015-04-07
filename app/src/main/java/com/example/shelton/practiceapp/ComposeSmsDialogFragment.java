package com.example.shelton.practiceapp;


import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class ComposeSmsDialogFragment extends DialogFragment {

    private final String[] COLUMNS = {ContactsContract.Contacts._ID,
                                      ContactsContract.Contacts.DISPLAY_NAME,
                                      ContactsContract.Contacts.HAS_PHONE_NUMBER};

    private EditText msg, pNumber;
    private Spinner spinContacts;
    private Cursor contacts;

    public ComposeSmsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compose_sms_dialog, container, false);

        contacts = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                COLUMNS, null, null, null);

        spinContacts = (Spinner) view.findViewById(R.id.spinner_contacts);
        spinContacts.setAdapter(new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item, contacts, null, null, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

        return view;
    }
}
