package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpRoomOptions extends HttpQueries {

    public HttpRoomOptions(String room, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/roomoptions.php", HttpQuery.mMethodGet, HttpQueries.rcRoomOptions);
        mQuery.mWebResponse = r;
        mQuery.setParameter("room", room);
    }
}