package com.booking.gson;

import com.google.gson.JsonObject;

public class GRoom extends GObject {
    public String id;
    public String pax;
    public String price;
    public String name;

    public static GRoom parse(JsonObject o) {
         return gson().fromJson(o, GRoom.class);
    }
}
