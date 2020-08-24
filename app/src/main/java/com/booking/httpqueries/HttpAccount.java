package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpAccount extends HttpQueries {

    public HttpAccount(HttpResponse r) {
        super(Cnf.mHttpHost + "/app/account.php", HttpQuery.mMethodPost, HttpQueries.rcAccount);
        mQuery.mWebResponse = r;
    }
}
