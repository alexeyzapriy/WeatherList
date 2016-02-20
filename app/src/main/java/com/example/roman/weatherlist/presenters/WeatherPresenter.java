package com.example.roman.weatherlist.presenters;

import com.example.roman.weatherlist.Consts;
import com.example.roman.weatherlist.Utils;
import com.example.roman.weatherlist.models.WeatherModel;

import java.util.Date;
import java.util.Locale;

public class WeatherPresenter {
    private WeatherModel mWeatherModel;

    public WeatherPresenter(WeatherModel weatherModel){
        mWeatherModel = weatherModel;
    }

    public String getIconUrl(){
        return String.format(Consts.WEATHER_ICON_URL, mWeatherModel.weather.get(0).icon);
    }

    public String getCity(){
        return mWeatherModel.name.toUpperCase(Locale.US);
    }

    public String getCountry(){
        return mWeatherModel.sys.country;
    }

    public String getDescription(){
        return mWeatherModel.weather.get(0).description.toUpperCase(Locale.US);
    }

    public String getHumidity(){
        return mWeatherModel.main_info.humidity.intValue() + "%";
    }

    public String getPressure(){
        return mWeatherModel.main_info.pressure.intValue() + " hPa";
    }

    public String getTempC(){
        return String.format("%.2f", mWeatherModel.main_info.temp) + " C";
    }

    public String getTempF(){
        double f = mWeatherModel.main_info.temp * 1.8 + 32;
        return String.format("%.2f", f) + " F";
    }

    public String getUpdateTime(){
        return Utils.formatDate(new Date(mWeatherModel.date * 1000));
    }
}
