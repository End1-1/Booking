package com.booking.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.booking.App;
import com.booking.R;

public class Cnf {
    private static final String KEY_TOKEN = "token";
    //public static final String mHttpHost = "https://192.168.2.66";
    //public static final String mHttpHost = "https://10.1.0.2";
    public static final String mHttpHost = "http://booking.newstarsoft.org";

    public static SharedPreferences getPreferences() {
        return App.context().getSharedPreferences(App.context().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor e = getPreferences().edit();
        e.putString(key, value).apply();
    }

    public static void setInt(String key, int value) {
        SharedPreferences.Editor e = getPreferences().edit();
        e.putInt(key, value).apply();
    }

    public static String getString(String key) {
        return getPreferences().getString(key, "");
    }

    public static int getInt(String key) {
        return getPreferences().getInt(key, 0);
    }

    public static void setToken(String token) {
        setString(KEY_TOKEN, token);
    }

    public static String getToken() {
        return getString(KEY_TOKEN);
    }
}
