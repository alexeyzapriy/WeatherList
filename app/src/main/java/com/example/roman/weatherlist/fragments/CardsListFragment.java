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
 * Use the {@link CardsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<WeatherModel> data = new ArrayList<>();
    private FrameLayout mMainContainer;
    private Cities cities;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CardsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardsListFragment newInstance(String param1, String param2) {
        CardsListFragment fragment = new CardsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        return inflater.inflate(R.layout.recycler_view, container, false);
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
                        data.add(weather);
                        if (data.size() == cities.length) onDataReceived();
                    } catch (Exception e) {
                        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
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

    private void onDataReceived() {
        RecyclerView list = (RecyclerView) LayoutInflater.from(getActivity()).inflate(R.layout.recycler_view, null);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(manager);
        CardsRecyclerAdapter adapter = new CardsRecyclerAdapter(getActivity(), data);
        list.setAdapter(adapter);
        mMainContainer.addView(list);
    }
}
