package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpQueryAddReserve extends HttpQueries {

    public HttpQueryAddReserve(String room, String message, String d1, String d2, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/addreserve.php", HttpQuery.mMethodPost, HttpQueries.rcAddReserve);
        mQuery.mWebResponse = r;
        mQuery.setParameter("room", room);
        mQuery.setParameter("message", message);
        mQuery.setParameter("checkin", d1);
        mQuery.setParameter("checkout", d2);
    }
}
