package com.example.roman.weatherlist;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd LLL", Locale.US);
        return sdf.format(date);
    }

    public static Boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
}
