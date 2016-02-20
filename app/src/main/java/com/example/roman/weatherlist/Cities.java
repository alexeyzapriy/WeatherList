package com.example.roman.weatherlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Cities {
    private SharedPreferences prefs;
    public Cities(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getMyCity(){
        return prefs.getString("myCity", "Kharkiv");
    }

    public void setMyCity(String city){
        prefs.edit().putString("myCity", city).commit();
    }

    public void setCities(String [] cities){
        String s = TextUtils.join(",", cities);
        prefs.edit().putString("cities", s).commit();
    }
    public String [] getCities(){
        String s = prefs.getString("cities", "Kharkiv");
        return TextUtils.split(s, ",");
    }
}
