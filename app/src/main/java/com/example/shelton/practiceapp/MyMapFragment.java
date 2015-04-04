package com.example.shelton.practiceapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;


public class MyMapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,
        DialogInterface.OnClickListener {

    private MapFragment mapFrag;
    private Button bNav;

    public MyMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_map, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapFrag = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.frag_map);
        mapFrag.getMapAsync(this);

        bNav = (Button) getActivity().findViewById(R.id.b_nav);
        bNav.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getCurrentLocation(), 15));
    }

    private LatLng getCurrentLocation() {
        try
        {
            LocationManager locMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String locProvider = locMgr.getBestProvider(criteria, false);
            Location location = locMgr.getLastKnownLocation(locProvider);

            // getting GPS status
            boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNWEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNWEnabled)
            {
                // no network provider is enabled
                return null;
            }
            else
            {
                // First get location from Network Provider
                if (isNWEnabled)
                    location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                    if (location == null)
                        location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (NullPointerException ne)
        {
            Log.e("Current Location", "Current Lat Lng is Null");
            return new LatLng(0, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new LatLng(0, 0);
        }
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("Navigation");
        builder.setView(inflater.inflate(R.layout.dialog_map, null));
        builder.setPositiveButton("Go", this);
        builder.setNegativeButton("Cancel", this);

        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == Dialog.BUTTON_POSITIVE) {
            Dialog dialog = (Dialog) dialogInterface;

            EditText address = (EditText) dialog.findViewById(R.id.in_address);
            RadioGroup transMode = (RadioGroup) dialog.findViewById(R.id.rg_transMode);
            //RadioButton rbDrive = (RadioButton) dialog.findViewById(R.id.rb_driving);
            RadioButton rbBike = (RadioButton) dialog.findViewById(R.id.rb_biking);
            RadioButton rbWalk = (RadioButton) dialog.findViewById(R.id.rb_walking);

            char mode;

            if(transMode.getCheckedRadioButtonId() == rbBike.getId())       mode = 'b';
            else if(transMode.getCheckedRadioButtonId() == rbWalk.getId())  mode = 'w';
            else                                                            mode = 'd';

            Log.d("RAWR Map", "mode: " + mode + " address: " + address.getText());

            Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="
                    + address.getText().toString() + "&mode=" + mode));
            startActivity(navIntent);

            dialogInterface.dismiss();
        } else {
            dialogInterface.cancel();
        }
    }
}
