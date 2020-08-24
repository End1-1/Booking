package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpSaveAccount extends HttpQueries {
    public HttpSaveAccount(String fname, String lname, String email, String phone, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/saveaccount.php", HttpQuery.mMethodPost, HttpQueries.rcSaveAccount);
        mQuery.mWebResponse = r;
        mQuery.setParameter("fname", fname);
        mQuery.setParameter("lname", lname);
        mQuery.setParameter("email", email);
        mQuery.setParameter("phone", phone);
    }
}
