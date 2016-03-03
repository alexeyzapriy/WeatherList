package com.example.roman.weatherlist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.roman.weatherlist.CardsRecyclerAdapter;
import com.example.roman.weatherlist.Cities;
import com.example.roman.weatherlist.Consts;
import com.example.roman.weatherlist.MySingleton;
import com.example.roman.weatherlist.R;
import com.example.roman.weatherlist.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CardsListFragment extends Fragment {
    private Cities cities;
    private ArrayList<WeatherModel> mData = new ArrayList<>();
    private CardsRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public CardsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cities = new Cities(getActivity());
        makeWeatherObj(cities.getCities());
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CardsRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    private void makeWeatherObj(final ArrayList<String> cities) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        for (int i = 0; i < cities.size(); i++) {
            String url = String.format(Consts.WEATHER_SERVICE_URL, cities.get(i));
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                        onDataReceived(weather, cities.size());
                    } catch (Exception e) {
                        Log.e("SimpleWeather", "One or more fields not found in the JSON data: " + e);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(getActivity()).addToRequestQueue(request);
        }

    }

    synchronized private void onDataReceived(WeatherModel weather, int total) {
        if (mData.size() == total) return;

        mData.add(weather);
        if (mData.size() == total) mAdapter.setDataSet(mData);
    }
}
