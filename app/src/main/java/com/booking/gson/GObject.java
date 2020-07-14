package com.booking.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GObject {

    protected static Gson gson() {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        return g;
    }
}
