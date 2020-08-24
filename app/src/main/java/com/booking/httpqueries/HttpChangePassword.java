package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpChangePassword extends HttpQueries {
    public HttpChangePassword(String currPass, String newPass, HttpResponse r) {
        super(Cnf.mHttpHost  + "/app/changepassword.php", HttpQuery.mMethodPost, HttpQueries.rcChangePassword);
        mQuery.mWebResponse = r;
        mQuery.setParameter("currpass", currPass);
        mQuery.setParameter("newpass", newPass);
    }
}
