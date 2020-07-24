package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpBookings extends HttpQueries {

    public HttpBookings(boolean arrives, boolean departures, boolean checkin, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/bookings.php", HttpQuery.mMethodGet, HttpQueries.rcBookings);
        mQuery.mWebResponse = r;
        if (arrives) {
            mQuery.setParameter("arrives", "1");
        }
        if (departures) {
            mQuery.setParameter("departures", "1");
        }
        if (checkin) {
            mQuery.setParameter("checkin", "1");
        }
    }
}
