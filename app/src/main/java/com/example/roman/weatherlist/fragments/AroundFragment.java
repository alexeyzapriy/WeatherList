package com.example.roman.weatherlist.fragments;

import android.app.Activity;
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
import com.android.volley.toolbox.StringRequest;
import com.example.roman.weatherlist.Consts;
import com.example.roman.weatherlist.MySingleton;
import com.example.roman.weatherlist.R;
import com.example.roman.weatherlist.models.SeveralCitiesWeatherModel;
import com.example.roman.weatherlist.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;


public class AroundFragment extends Fragment {

    private View view;

    private static final String TAG = "MyCity";
    private SharedPreferences prefs;

    public AroundFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_around, container, false);
        prefs = getActivity().getPreferences(Activity.MODE_PRIVATE);
        String coord = prefs.getString("coord", "");
        if(coord.equals("")){
            Log.i("!!!!!!!!!!!!!", "I don't know my location " + coord);
        }else{
            String new_str = coord.replace (',', '.');
            String[] arrCoord = new_str.split("_");
            makeWeatherObjLoc(arrCoord[0], arrCoord[1]);
        }


        return view;
    }

    private void makeWeatherObjLoc(String lat, String lon) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        String units = prefs.getString("units", "metric");
        final String url = String.format(Consts.OPEN_WEATHER_MAP_API_COORD_CITIES_IN_CYCLE, lat, lon, 5, units, Consts.OPEN_WEATHER_MAP_API_APP_ID);

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        SeveralCitiesWeatherModel weather = gson.fromJson(response, SeveralCitiesWeatherModel.class);
                        fillTheFields(weather.list);
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

    }

    private void fillTheFields(List<WeatherModel> wm) {
        TextView tvResp = (TextView) view.findViewById(R.id.json_response);
        String str = "";
        for (int i = 0; i < wm.size(); i++) {
            str += wm.get(i).name + " ";
        }
        tvResp.setText(str);
    }

}
