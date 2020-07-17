package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpBookingVoucher extends HttpQueries {
    public HttpBookingVoucher(String id, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/bookingvoucher.php", HttpQuery.mMethodGet, HttpQueries.rcBookingVoucher);
        mQuery.setParameter("bookingid", id);
        mQuery.mWebResponse = r;
    }
}
