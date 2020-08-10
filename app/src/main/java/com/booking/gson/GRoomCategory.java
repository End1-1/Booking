package com.booking.gson;

import java.util.ArrayList;
import java.util.List;

public class GRoomCategory extends GObject {
    public String id;
    public String name;
    public String pax;
    public static List<GRoomCategory> mList = new ArrayList<>();
    public static int pos(String id) {
        for (GRoomCategory c: mList) {
            if (c.id.equals(id)) {
                return mList.indexOf(c);
            }
        }
        return -1;
    }
}
