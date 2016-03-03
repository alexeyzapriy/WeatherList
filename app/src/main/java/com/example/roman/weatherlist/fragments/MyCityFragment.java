package com.example.roman.weatherlist.fragments;


import android.content.Context;
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

import java.util.ArrayList;

public class MyCityFragment extends Fragment {
    private Context context;
    private Cities cities;
    private View view;
    private ImageLoader mImageLoader = MySingleton.getInstance(context).getImageLoader();

    public MyCityFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_city, container, false);
        context = getActivity();
        cities = new Cities(getActivity());
        makeWeatherObj(cities.getMyCity());
        return view;
    }

    private void makeWeatherObj(String city) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        String url = String.format(Consts.WEATHER_SERVICE_URL, city);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Weather Response", response);
                try {
                    WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                    fillTheFields(weather);
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

    private void fillTheFields(WeatherModel wm) {
        TextView tvCity = (TextView) view.findViewById(R.id.city_field);
        TextView tvUpdated = (TextView) view.findViewById(R.id.updated_field);
        TextView tvDetails = (TextView) view.findViewById(R.id.details_field);
        TextView tvTemperat = (TextView) view.findViewById(R.id.current_temperature_field);
        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.weather_icon);
        WeatherPresenter weatherPresenter = new WeatherPresenter(wm);

        networkImageView.setImageUrl(weatherPresenter.getIconUrl(), mImageLoader);
        tvCity.setText(weatherPresenter.getCity() + ", " + weatherPresenter.getCountry());
        tvDetails.setText(weatherPresenter.getDescription()
                + "\n" + "Humidity: "
                + weatherPresenter.getHumidity()
                + "\n" + "Pressure: "
                + weatherPresenter.getPressure());
        tvTemperat.setText(weatherPresenter.getTempC());
        tvUpdated.setText("Last update: " + weatherPresenter.getUpdateTime());
    }
}
