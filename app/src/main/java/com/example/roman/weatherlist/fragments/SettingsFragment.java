package com.example.roman.weatherlist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.roman.weatherlist.R;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_view, container, false);
        RadioGroup radiogroup = (RadioGroup)view.findViewById(R.id.rgr);


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbC:
                        Toast.makeText(getActivity(), "C", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbF:
                        Toast.makeText(getActivity(), "F", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        return view;

    }

}
