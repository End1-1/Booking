package com.booking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.booking.R;
import com.booking.databinding.ActivityRoomEditBinding;
import com.booking.httpqueries.HttpSaveRoom;

public class RoomEditActivity extends ParentActivity {

    private String mId;
    private ActivityRoomEditBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityRoomEditBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        Intent i = getIntent();
        mId = i.getStringExtra("room");
        mBind.name.setText(i.getStringExtra("name"));
        mBind.place.setText(i.getStringExtra("pax"));
        mBind.price.setText(i.getStringExtra("price"));
        mBind.save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                mBind.progress.setVisibility(View.VISIBLE);
                HttpSaveRoom saveRoom = new HttpSaveRoom(mId, mBind.name.getText().toString(), mBind.place.getText().toString(), mBind.price.getText().toString(), this);
                saveRoom.go();
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.progress.setVisibility(View.GONE);
    }
}