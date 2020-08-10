package com.booking.activities;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    static public App getInstance() {
        return mInstance;
    }

    static public Context context() {
        return getInstance().getBaseContext();
    }
}
