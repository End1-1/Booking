package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpBookings extends HttpQueries {

    public HttpBookings(HttpResponse r) {
        super(Cnf.mHttpHost + "/app/bookings.php", HttpQuery.mMethodGet, HttpQueries.rcBookings);
        mQuery.mWebResponse = r;
    }
}
