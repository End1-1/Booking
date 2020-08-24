package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

import java.net.HttpRetryException;

public class HttpAutologin extends HttpQueries {

    public HttpAutologin(HttpResponse r) {
        super(Cnf.mHttpHost + "/app/autologin.php", HttpQuery.mMethodPost, HttpQueries.rcAutologin);
        mQuery.mWebResponse = r;
        mQuery.setParameter("token", Cnf.getToken());
        mQuery.setParameter("id", Cnf.getString("user_id"));
    }
}
