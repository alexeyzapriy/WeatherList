package com.example.roman.weatherlist.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CardsListFragment extends Fragment {

    public static final String IS_MY_CITY = "isMyCity";
    private FrameLayout mMainContainer;
    private Cities cities;
    private boolean mIsMyCity;
    private ArrayList<WeatherModel> mData = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private CardsRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public CardsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsMyCity = getArguments().getBoolean(IS_MY_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] arrCities = {"lviv", "london", "rome", "kharkiv", "zmiiv"};
        cities = new Cities(getActivity());
        cities.setMyCity("zmiiv");
        cities.setCities(arrCities);
        makeWeatherObj(cities.getCities());
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CardsRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void makeWeatherObj(final String[] cities) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        for (int i = 0; i < cities.length; i++) {
            String url = String.format(Consts.WEATHER_SERVICE_URL, cities[i]);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                        onDataReceived(weather, cities.length);
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
