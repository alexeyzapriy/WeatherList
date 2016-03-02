package com.example.roman.weatherlist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roman.weatherlist.CitiesListRecyclerViewAdapter;
import com.example.roman.weatherlist.R;

public class CitiesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";


    public CitiesListFragment() {

    }

    public static CitiesListFragment newInstance(int columnCount) {
        CitiesListFragment fragment = new CitiesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new CitiesListRecyclerViewAdapter(getActivity()));
        }
        return view;
    }

}
