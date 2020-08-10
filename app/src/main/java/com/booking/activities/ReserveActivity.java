package com.booking.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.booking.R;
import com.booking.databinding.ActivityReserveBinding;
import com.booking.gson.GAnswer;
import com.booking.httpqueries.HttpQueryAddReserve;
import com.booking.utils.Cmn;
import com.booking.utils.Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReserveActivity extends ParentActivity {

    private ActivityReserveBinding bind;
    private String mId;
    private int mDateMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityReserveBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        mId = getIntent().getStringExtra("id");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        bind.checkin.setText(df.format(Calendar.getInstance().getTime()));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        bind.name.setText(getIntent().getStringExtra("name"));
        bind.checkout.setText(df.format(c.getTime()));
        bind.addreserve.setOnClickListener(this);
        bind.btnStart.setOnClickListener(this);
        bind.addreserve.requestFocus();
        bind.btnEnd.setOnClickListener(this);
        bind.calendarView.setOnClickListener(this);
        bind.calendarView.setMinDate(Calendar.getInstance().getTime().getTime());
        bind.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                bind.cv.setVisibility(View.INVISIBLE);
                switch (mDateMode) {
                    case 1:
                        bind.checkin.setText(String.format("%02d/%02d/%d", i2, i1+1, i));
                        break;
                    case 2:
                        bind.checkout.setText(String.format("%02d/%02d/%d", i2, i1+1, i));
                        break;
                }
                mDateMode = 0;
            }
        });
    }

    @Override
    public void onClick(View view) {
        //Cmn.hideSoftKeyboard();
        bind.cr.setVisibility(View.GONE);
        bind.cv.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.addreserve:
                bind.progress.setVisibility(View.VISIBLE);
                HttpQueryAddReserve addReserve = new HttpQueryAddReserve(mId, bind.message.getText().toString(), bind.checkin.getText().toString(), bind.checkout.getText().toString(), this);
                addReserve.go();
                break;
            case R.id.btnStart:
                bind.cv.setVisibility(View.VISIBLE);
                mDateMode = 1;
                break;
            case R.id.btnEnd:
                bind.cv.setVisibility(View.VISIBLE);
                mDateMode = 2;
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        bind.progress.setVisibility(View.GONE);
        Cmn.hideSoftKeyboard();
        if (webResponse > 299) {
            bind.response.setText(s);
            bind.cr.setVisibility(View.VISIBLE);
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        if (ga.ok == 0) {
            bind.cr.setVisibility(View.VISIBLE);
            bind.response.setText(ga.msg);
            return;
        }
        Dialog.alertDialog(this, R.string.Empty, getString(R.string.Saved), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }
}