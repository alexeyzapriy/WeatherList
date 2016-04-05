package com.example.roman.weatherlist;

public class Consts {
    //public static final String WEATHER_SERVICE_URL="https://agile-plains-64462.herokuapp.com/current?city=%s";
    public static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s";
    public static final String OPEN_WEATHER_MAP_API_COORD = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=%s&appid=%s";
    public static final String OPEN_WEATHER_MAP_API_COORD_CITIES_IN_CYCLE = "http://api.openweathermap.org/data/2.5/find?lat=%s&lon=%s&cnt=%s&units=%s&appid=%s";
    public static final String WEATHER_ICON_URL="http://openweathermap.org/img/w/%s.png";
    public static final String OPEN_WEATHER_MAP_API_APP_ID = "a41283afb1824a7893a04e68e1eb543a";
    static final int CACHE_MAX_AGE = 4*60*60*1000;
}
