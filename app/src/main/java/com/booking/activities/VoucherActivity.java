package com.booking.activities;

import android.content.DialogInterface;
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
import com.booking.utils.Dialog;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class VoucherActivity extends ParentActivity {

    private String mId;
    private int mState = 0;
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
                updateState("3");
                break;
            case R.id.checkout:
                updateState("4");
                break;
            case R.id.cancelReserve:
                updateState("5");
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
                mState = Integer.valueOf(bv.state);
                setButtons();
                break;
            case HttpQueries.rcReserveState:
                finish();
                break;
        }
    }

    void updateState(final String state) {
        Dialog.alertDialog(this, R.string.SaveChanges, getString(R.string.SaveChanges), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                switch (i) {
                    case BUTTON_POSITIVE:
                        HttpReservationState rcheckin = new HttpReservationState(mId, state, VoucherActivity.this);
                        rcheckin.go();
                        break;
                }
            }
        });
    }

    void setButtons() {switch (mState) {
        case 2:
            mBind.checkout.setVisibility(View.GONE);
            break;
        case 3:
            mBind.checkin.setVisibility(View.GONE);
            mBind.cancelReserve.setVisibility(View.GONE);
            break;
        default:
            mBind.checkin.setVisibility(View.GONE);
            mBind.checkout.setVisibility(View.GONE);
            mBind.cancelReserve.setVisibility(View.GONE);
            break;
        }
    }
}