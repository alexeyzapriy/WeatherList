package com.example.roman.weatherlist.models;

import com.google.gson.annotations.SerializedName;

public class WeatherConditionsModel {
    @SerializedName("main")
    public String main_title;
    public String description;
    public String icon;
}
