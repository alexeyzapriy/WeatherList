package com.example.roman.weatherlist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd LLL", Locale.US);
        return sdf.format(date);
    }
}
