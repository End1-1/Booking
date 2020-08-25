package com.booking.httpqueries;

import com.booking.utils.Cnf;
import com.booking.utils.HttpQuery;

public class HttpQueries {

    public static final int rcAuth = 1;
    public static final int rcAutologin = 2;
    public static final int rcBookings = 3;
    public static final int rcRooms = 4;
    public static final int rcBookingVoucher = 5;
    public static final int rcSaveRoom = 6;
    public static final int rcAddReserve = 7;
    public static final int rcReserveState = 8;
    public static final int rcRoomOptions = 9;
    public static final int rcSaveRoomOptions = 10;
    public static final int rcRoomCategoryList = 11;
    public static final int rcChangePassword = 12;
    public static final int rcSaveAccount = 13;
    public static final int rcAccount = 14;
    public static final int rcNames = 15;
    public static final int rcSaveName = 16;
    public static final int rcRoomClass = 17;
    public static final int rcSaveRoomClass = 18;
    public static final int rcRoomParameter = 19;
    public static final int rcSaveRoomParameter = 20;

    public HttpQuery mQuery;

    public HttpQueries(String url, String method, int resultCode) {
        mQuery = new HttpQuery(url, method, resultCode);
        mQuery.setParameter("token", Cnf.getToken());
    }

    public void setParameter(String key, String value) {
        mQuery.setParameter(key, value);
    }

    public void go() {
        mQuery.request();
    }
}
