package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpLogin extends  HttpQueries{

    public HttpLogin(String username, String password, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/auth.php", HttpQuery.mMethodPost, HttpQueries.rcAuth);
        mQuery.mWebResponse = r;
        mQuery.setParameter("username", username);
        mQuery.setParameter("password", password);
        mQuery.setParameter("token", Cnf.getToken());
    }
}
