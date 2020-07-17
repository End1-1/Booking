package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpSaveRoom extends HttpQueries {
    public HttpSaveRoom(String room, String name, String pax, String price, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/saveroom.php", HttpQuery.mMethodPost, HttpQueries.rcSaveRoom);
        mQuery.mWebResponse = r;
        mQuery.setParameter("room", room);
        mQuery.setParameter("name", name);
        mQuery.setParameter("pax", pax);
        mQuery.setParameter("price", price);
    }
}