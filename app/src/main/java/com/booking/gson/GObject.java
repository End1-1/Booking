package com.booking.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.List;

public class GObject  {

    protected static Gson gson() {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        return g;
    }

    public static <T> T parse(JsonObject s, Class<T> t) {
        return gson().fromJson(s, (Type) t);
    }

    public static <T> T parse(String s, Class<T> t) {
        return gson().fromJson(s, (Type) t);
    }

    public static <T> void from (JsonArray a, Class<T> t, List<T> l) {
        l.clear();
        for (int i = 0; i < a.size(); i++) {
            T go = gson().fromJson(a.get(i).getAsJsonObject(), (Type) t);
            l.add(go);
        }
    }
}
