package com.example.roman.weatherlist.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
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
    private static final String TAG = "CardsList";
    private SharedPreferences prefs;

    public CardsListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CardsRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        cities = new Cities(getActivity());
        prefs = getActivity().getPreferences(Activity.MODE_PRIVATE);
        makeWeatherObj(cities.getCities());
        return mRecyclerView;
    }

    private void makeWeatherObj(final ArrayList<String> cities) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        String units = prefs.getString("units", "metric");
        for (int i = 0; i < cities.size(); i++) {
            final String url = String.format(Consts.OPEN_WEATHER_MAP_API, cities.get(i), units, Consts.OPEN_WEATHER_MAP_API_APP_ID);

            WeatherModel weather = MySingleton.getInstance(getActivity()).fetchFromVolleyCache(url, WeatherModel.class);
            if (weather == null) {
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("Response(web)", response);
                        try {
                            WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                            onDataReceived(weather, cities.size());
                        } catch (Exception e) {
                            Log.e(TAG, "fetch data from web (" + url + "): " + e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                MySingleton.getInstance(getActivity()).addToRequestQueue(request);

            } else {
                try {
                    onDataReceived(weather, cities.size());
                } catch (Exception e) {
                    Log.e(TAG, "fetch data from cache(" + url + "): " + e);
                }
            }
        }

    }

    synchronized private void onDataReceived(WeatherModel weather, int total) {
        if (mData.size() == total) return;

        mData.add(weather);
        if (mData.size() == total) mAdapter.setDataSet(mData);
    }
}
