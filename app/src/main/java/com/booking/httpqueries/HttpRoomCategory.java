package com.booking.httpqueries;

import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpRoomCategory extends HttpQueries {
    public HttpRoomCategory(HttpResponse r) {
        super(Cnf.mHttpHost + "/app/categorylist.php", HttpQuery.mMethodPost, HttpQueries.rcRoomCategoryList);
        mQuery.mWebResponse = r;
    }

}
