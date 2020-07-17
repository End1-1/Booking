package com.booking.httpqueries;

import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpQueries {

    public static final int rcAuth = 1;
    public static final int rcAutologin = 2;
    public static final int rcBookings = 3;
    public static final int rcRooms = 4;

    protected HttpQuery mQuery;

    public HttpQueries(String url, String method, int resultCode) {
        mQuery = new HttpQuery(url, method, resultCode);
        mQuery.setParameter("token", Cnf.getToken());
    }

    public void go() {
        mQuery.request();
    }
}
