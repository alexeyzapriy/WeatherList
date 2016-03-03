package com.example.roman.weatherlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;

public class Cities {
    private SharedPreferences prefs;

    public Cities(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getMyCity() {
        return prefs.getString("myCity", "");
    }

    public void setMyCity(String city) {
        prefs.edit().putString("myCity", city).commit();
        addCity(city);
    }

    private void addCity(String city) {
        if (!prefs.contains("cities")) {
            prefs.edit().putString("cities", city).commit();
        } else {
            String s = prefs.getString("cities", "");
            String[] arr = TextUtils.split(s, ",");
            boolean flag = false;
            for (String i : arr) {
                if (i.compareToIgnoreCase(city) == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                prefs.edit().putString("cities", s + "," + city).commit();
            }
        }
    }

    public void setCities(ArrayList<String> cities) {
        String s = TextUtils.join(",", cities);
        prefs.edit().putString("cities", s).commit();
    }

    public ArrayList<String> getCities() {
        ArrayList arr = new ArrayList();
        if (prefs.contains("cities")) {
            String s = prefs.getString("cities", "");
            Collections.addAll(arr, TextUtils.split(s, ","));
            return arr;
        } else {
            arr.add("zmiiv");
            prefs.edit().putString("cities", "zmiiv").commit();
            return arr;
        }
    }

    public void setCity(String city) {
        addCity(city);
    }
}
