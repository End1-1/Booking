package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpReservationState extends HttpQueries {

    public HttpReservationState(String reserveId, String state, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/reservestate.php", HttpQuery.mMethodPost, HttpQueries.rcReserveState);
        mQuery.mWebResponse = r;
        mQuery.setParameter("resid", reserveId);
        mQuery.setParameter("resstate", state);
    }
}
