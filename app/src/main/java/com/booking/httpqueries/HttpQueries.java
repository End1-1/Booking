package com.booking.httpqueries;

import com.booking.utils.HttpQuery;

public class HttpQueries {

    public static final int rcAuth = 1;
    public static final int rcAutologin = 2;

    protected HttpQuery mQuery;

    public HttpQueries(String url, String method, int resultCode) {
        mQuery = new HttpQuery(url, method, resultCode);
    }

    public void go() {
        mQuery.request();
    }
}
