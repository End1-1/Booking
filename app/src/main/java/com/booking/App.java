package com.booking;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static App mInstance;

    public App() {
        super();
        mInstance = this;
    }

    public static Context context() {
        return mInstance.getBaseContext();
    }
}
