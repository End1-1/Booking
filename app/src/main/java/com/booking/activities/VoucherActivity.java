package com.booking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.booking.R;
import com.booking.databinding.ActivityVoucherBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GBookingVoucher;
import com.booking.httpqueries.HttpBookingVoucher;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpReservationState;

public class VoucherActivity extends ParentActivity {

    String mId;
    private ActivityVoucherBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        Intent i = getIntent();
        mId = i.getStringExtra("id");
        mBind.bookingId.setText(String.format("#%s", mId));
        mBind.checkin.setOnClickListener(this);
        mBind.checkout.setOnClickListener(this);
        mBind.cancelReserve.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpBookingVoucher bookingVoucher = new HttpBookingVoucher(mId, this);
        bookingVoucher.go();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkin:
                HttpReservationState rcheckin = new HttpReservationState(mId, "3", this);
                rcheckin.go();
                break;
            case R.id.checkout:
                HttpReservationState rcheckout = new HttpReservationState(mId, "4", this);
                rcheckout.go();
                break;
            case R.id.cancelReserve:
                HttpReservationState rcancel = new HttpReservationState(mId, "5", this);
                rcancel.go();
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.progress.setVisibility(View.GONE);
        if (webResponse > 299) {
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        if (ga == null || ga.ok == 0) {
            return;
        }
        switch (code) {
            case HttpQueries.rcBookingVoucher:
                GBookingVoucher bv = GBookingVoucher.parse(ga.data, GBookingVoucher.class);
                mBind.room.setText(bv.room);
                mBind.arrival.setText(bv.arrival);
                mBind.departure.setText(bv.departure);
                mBind.guest.setText(bv.guest);
                mBind.phone.setText(bv.phone);
                mBind.email.setText(bv.email);
                mBind.adults.setText(bv.adults);
                mBind.childs.setText(bv.childs);
                mBind.price.setText(bv.price);
                mBind.nights.setText(bv.nights);
                mBind.total.setText(bv.total);
                mBind.message.setText(bv.message);
                break;
            case HttpQueries.rcReserveState:
                finish();
                break;
        }
    }
}