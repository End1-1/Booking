package com.booking.gson;

import com.google.gson.JsonObject;

public class GBookings extends GObject {
    public String id;
    public String room;
    public String datefrom;
    public String dateto;
    public String nights;
    public String price;
    public String total;
    public String pax;
    public String timefor;
    public String restype;
    public String client;

    public static GBookings parse(JsonObject o) {
        return gson().fromJson(o, GBookings.class);
    }
}
