package com.booking.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GUser extends GObject {
    public int id;
    public String firstname;
    public String lastname;
    public String phone;
    public String email;

    public static GUser parse(JsonObject o) {
        return gson().fromJson(o, GUser.class);
    }
}
