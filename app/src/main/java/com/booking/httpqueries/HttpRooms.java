package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpRooms extends HttpQueries {

    public HttpRooms(HttpResponse r) {
        super(Cnf.mHttpHost + "/app/rooms.php", HttpQuery.mMethodGet, HttpQueries.rcRooms);
        mQuery.mWebResponse = r;
    }
}
