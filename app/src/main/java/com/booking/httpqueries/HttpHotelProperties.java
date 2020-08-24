package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpHotelProperties extends HttpQueries {

    public HttpHotelProperties(String room, String group, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/roomoptions.php", HttpQuery.mMethodGet, HttpQueries.rcRoomOptions);
        mQuery.mWebResponse = r;
        mQuery.setParameter("room", room);
        mQuery.setParameter("group", group);
    }
}