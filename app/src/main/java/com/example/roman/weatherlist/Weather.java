package com.example.roman.weatherlist;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Roman on 16.02.2016.
 */
public class Weather {
    private String cityName;
    private String country;
    private String description;
    private String humidity;
    private String pressure;
    private double temp;
    private Date dt;
    private Bitmap iconWeather;

    public Weather(JSONObject j){

        try {
            JSONObject details = j.getJSONArray("weather").getJSONObject(0);
            JSONObject main = j.getJSONObject("main");

            cityName = j.getString("name").toUpperCase(Locale.US);
            country = j.getJSONObject("sys").getString("country");
            description = details.getString("description").toUpperCase(Locale.US);
            humidity = main.getString("humidity") + "%";
            pressure = main.getString("pressure") + " hPa";
            temp = main.getDouble("temp");
            dt = new Date(j.getLong("dt")*1000);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setIconWeather(Bitmap bitmap){
        iconWeather = bitmap;
    }

    public Bitmap getBitmap(){
        return iconWeather;
    }

    public String getCityName(){
        return cityName;
    }

    public String getCountry(){
        return country;
    }

    public String getDescription(){
        return description;
    }

    public String getHumidity(){
        return humidity;
    }

    public String getPressure(){
        return pressure;
    }

    public String getTemp(boolean isC) {
        if (isC) {
            return String.format("%.2f", temp + " C");
        } else {
            double f = temp * 1.8 + 32;
            return String.format("%.2f", f + " F");
        }
    }

    public String getDate(){
        DateFormat df = DateFormat.getDateInstance();
        return df.format(dt);
    }
}
