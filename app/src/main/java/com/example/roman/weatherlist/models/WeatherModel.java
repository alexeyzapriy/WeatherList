package com.example.roman.weatherlist.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherModel {
    public List<WeatherConditionsModel> weather = new ArrayList<WeatherConditionsModel>();
    @SerializedName("main")
    public MainInfoModel main_info;
    @SerializedName("dt")
    public Long date;
    public SystemModel sys;
    public String name;
}
