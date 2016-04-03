package com.example.roman.weatherlist.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.example.roman.weatherlist.MySingleton;
import com.example.roman.weatherlist.R;
import com.google.android.gms.location.LocationRequest;

import java.util.Date;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscription;
import rx.functions.Action1;

public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;
    private Activity activity;
    private Subscription subscription;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
        subscription.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_view, container, false);
        activity = getActivity();
        RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.rgr);
        prefs = getActivity().getPreferences(Activity.MODE_PRIVATE);
        String units = prefs.getString("units", "metric");
        final Cache cache = MySingleton.getInstance(getActivity()).getRequestQueue().getCache();
        if (units.equals("metric")) {
            RadioButton rbc = (RadioButton) view.findViewById(R.id.rbC);
            rbc.setChecked(true);

        } else {
            RadioButton rbf = (RadioButton) view.findViewById(R.id.rbF);
            rbf.setChecked(true);
        }

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbC:
                        prefs.edit().putString("units", "metric").commit();
                        cache.clear();
                        break;
                    case R.id.rbF:
                        prefs.edit().putString("units", "imperial").commit();
                        cache.clear();
                        break;
                    default:
                        break;
                }
            }
        });

        Button btnGetLocation = (Button) view.findViewById(R.id.button_get_location);
        final TextView myLoc = (TextView) view.findViewById(R.id.my_coord);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationRequest request = LocationRequest.create();

                ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(activity);
                subscription = locationProvider.getUpdatedLocation(request)
                        .subscribe(new Action1<Location>() {
                            @Override
                            public void call(Location location) {
                                myLoc.setText(String.format(
                                        "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                                        location.getLatitude(), location.getLongitude(), new Date(
                                                location.getTime())));

                                prefs.edit().putString("coord", location.getLatitude() + "_" + location.getLongitude()).commit();
                            }
                        });

            }
        });

        return view;

    }

}
