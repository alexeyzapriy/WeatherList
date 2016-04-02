package com.example.roman.weatherlist.presenters;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.roman.weatherlist.Consts;
import com.example.roman.weatherlist.Utils;
import com.example.roman.weatherlist.models.WeatherModel;

import java.util.Date;
import java.util.Locale;

public class WeatherPresenter {
    private WeatherModel mWeatherModel;
    private String units;

    public WeatherPresenter(WeatherModel weatherModel, Activity activity) {

        mWeatherModel = weatherModel;
        SharedPreferences prefs = activity.getPreferences(Activity.MODE_PRIVATE);
        units = prefs.getString("units", "metric");
    }

    public String getIconUrl() {
        return String.format(Consts.WEATHER_ICON_URL, mWeatherModel.weather.get(0).icon);
    }

    public String getCity() {
        return mWeatherModel.name.toUpperCase(Locale.US);
    }

    public String getCountry() {
        return mWeatherModel.sys.country;
    }

    public String getDescription() {
        return mWeatherModel.weather.get(0).description.toUpperCase(Locale.US);
    }

    public String getHumidity() {
        return mWeatherModel.main_info.humidity.intValue() + "%";
    }

    public String getPressure() {
        return mWeatherModel.main_info.pressure.intValue() + " hPa";
    }

    public String getTemp() {
        String unit;
        if(units.equals("metric")){
            unit = " C" + '\u00B0';
        }else{
            unit = " F" + "\u00B0";
        }
        return String.format("%.2f", mWeatherModel.main_info.temp) + unit;
    }

    public String getUpdateTime() {
        return Utils.formatDate(new Date(mWeatherModel.date * 1000));
    }
}
