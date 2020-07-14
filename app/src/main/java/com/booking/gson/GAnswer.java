package com.booking.gson;

import com.google.gson.JsonObject;

public class GAnswer extends GObject{
    public int ok;
    public String msg;
    public JsonObject data;

    public static GAnswer parse(String s) {
        GAnswer ga = gson().fromJson(s, GAnswer.class);
        return ga;
    }
}