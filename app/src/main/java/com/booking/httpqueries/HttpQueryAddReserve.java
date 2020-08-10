package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpQueryAddReserve extends HttpQueries {

    public HttpQueryAddReserve(String room, String message, String d1, String d2, HttpResponse r) {
        super(Cnf.mHttpHost + "/app/addreserve.php", HttpQuery.mMethodPost, HttpQueries.rcAddReserve);
        mQuery.mWebResponse = r;
        mQuery.setParameter("room", room);
        mQuery.setParameter("message", message);
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mQuery.setParameter("checkin", df2.format(df1.parse(d1)));
            mQuery.setParameter("checkout", df2.format(df1.parse(d2)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
