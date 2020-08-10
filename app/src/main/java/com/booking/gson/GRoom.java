package com.booking.gson;

import java.util.ArrayList;
import java.util.List;

public class GRoom extends GObject {
    public String id;
    public String category;
    public String pax;
    public String price;
    public String name;
    public static List<GRoom> mList = new ArrayList<>();
}
