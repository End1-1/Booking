package com.booking.httpqueries;

import com.booking.gson.GRoomOptions;
import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpSaveRoomOptions extends HttpQueries {
    public HttpSaveRoomOptions(HttpResponse r, String group) {
        super(Cnf.mHttpHost + "/app/saveroomoptions.php", HttpQuery.mMethodPost, HttpQueries.rcSaveRoomOptions);
        mQuery.mWebResponse = r;
        String options = "";
        for (GRoomOptions ro: GRoomOptions.mList) {
            if (!group.isEmpty()) {
                if (!group.equals(ro.fparamtype)) {
                    continue;
                }
            }
            if (ro.fcheck > 0) {
                if (options.length() > 0) {
                    options += ",";
                }
                options += ro.fid;
            }
        }
        mQuery.setParameter("object", Cnf.getString("object"));
        mQuery.setParameter("options", options);
        mQuery.setParameter("group", group);
    }
}
