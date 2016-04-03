package com.example.roman.weatherlist.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.roman.weatherlist.Cities;
import com.example.roman.weatherlist.Consts;
import com.example.roman.weatherlist.MySingleton;
import com.example.roman.weatherlist.R;
import com.example.roman.weatherlist.models.WeatherModel;
import com.example.roman.weatherlist.presenters.WeatherPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyCityFragment extends Fragment {
    private Context context;
    private Cities cities;
    private View view;
    private ImageLoader mImageLoader = MySingleton.getInstance(context).getImageLoader();
    private static final String TAG = "MyCity";
    private SharedPreferences prefs;

    public MyCityFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_city, container, false);
        context = getActivity();
        cities = new Cities(getActivity());
        prefs = getActivity().getPreferences(Activity.MODE_PRIVATE);
        String coord = prefs.getString("coord", "");
        if(coord.equals("")){
            makeWeatherObj(cities.getMyCity());
        }else{
            String new_str = coord.replace (',', '.');
            String[] arrCoord = new_str.split("_");
            makeWeatherObjLoc(arrCoord[0], arrCoord[1]);
        }


        return view;
    }

    private void makeWeatherObj(String city) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        String units = prefs.getString("units", "metric");
        final String url = String.format(Consts.OPEN_WEATHER_MAP_API, city, units, Consts.OPEN_WEATHER_MAP_API_APP_ID);

        WeatherModel weather = MySingleton.getInstance(getActivity()).fetchFromVolleyCache(url, WeatherModel.class);
        if (weather == null) {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                        fillTheFields(weather);
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
            fillTheFields(weather);
        }
    }

    private void makeWeatherObjLoc(String lat, String lon) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        String units = prefs.getString("units", "metric");
        final String url = String.format(Consts.OPEN_WEATHER_MAP_API_COORD, lat, lon, units, Consts.OPEN_WEATHER_MAP_API_APP_ID);

        WeatherModel weather = MySingleton.getInstance(getActivity()).fetchFromVolleyCache(url, WeatherModel.class);
        if (weather == null) {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        WeatherModel weather = gson.fromJson(response, WeatherModel.class);

                        fillTheFields(weather);
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
            fillTheFields(weather);
        }
    }

    private void fillTheFields(WeatherModel wm) {
        TextView tvCity = (TextView) view.findViewById(R.id.city_field);
        TextView tvUpdated = (TextView) view.findViewById(R.id.updated_field);
        TextView tvDetails = (TextView) view.findViewById(R.id.details_field);
        TextView tvTemperat = (TextView) view.findViewById(R.id.current_temperature_field);
        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.weather_icon);
        WeatherPresenter weatherPresenter = new WeatherPresenter(wm, getActivity());

        networkImageView.setImageUrl(weatherPresenter.getIconUrl(), mImageLoader);
        tvCity.setText(weatherPresenter.getCity() + ", " + weatherPresenter.getCountry());
        tvDetails.setText(weatherPresenter.getDescription()
                + "\n" + "Humidity: "
                + weatherPresenter.getHumidity()
                + "\n" + "Pressure: "
                + weatherPresenter.getPressure());
        tvTemperat.setText(weatherPresenter.getTemp());
        tvUpdated.setText("Last update: " + weatherPresenter.getUpdateTime());
    }
}
