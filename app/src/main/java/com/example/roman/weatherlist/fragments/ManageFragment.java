package com.example.roman.weatherlist.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.roman.weatherlist.Cities;
import com.example.roman.weatherlist.R;

public class ManageFragment extends Fragment {
    public ManageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_view, container, false);
        Button chMy = (Button) view.findViewById(R.id.button_change_my);
        Button addCity = (Button) view.findViewById(R.id.button_add_city);
        Button shList = (Button) view.findViewById(R.id.button_show_list);

        chMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cities cities = new Cities(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change my city");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(cities.getMyCity());
                builder.setView(input);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cities.setMyCity(input.getText().toString());
                    }
                });
                builder.show();
            }
        });

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Cities cities = new Cities(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add city");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cities.setCity(input.getText().toString());
                    }
                });
                builder.show();
            }
        });

        shList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new CitiesListFragment())
                        .commit();
            }
        });

        return view;
    }
}
