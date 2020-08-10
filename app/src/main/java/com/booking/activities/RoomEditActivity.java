package com.booking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.booking.R;
import com.booking.adapters.CategoryAdapter;
import com.booking.databinding.ActivityRoomEditBinding;
import com.booking.gson.GRoomCategory;
import com.booking.httpqueries.HttpSaveRoom;
import com.google.gson.JsonParser;

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
        mBind.addreserve.setOnClickListener(this);
        mBind.category.setAdapter(new CategoryAdapter(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                mBind.progress.setVisibility(View.VISIBLE);
                GRoomCategory rc = (GRoomCategory) mBind.category.getSelectedItem();
                HttpSaveRoom saveRoom = new HttpSaveRoom(mId, rc.id, mBind.name.getText().toString(), mBind.place.getText().toString(), mBind.price.getText().toString(), this);
                saveRoom.go();
                break;
            case R.id.addreserve:
                Intent ri = new Intent(this, ReserveActivity.class);
                ri.putExtra("id", mId);
                ri.putExtra("name", mBind.name.getText().toString());
                startActivity(ri);
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.progress.setVisibility(View.GONE);
        JsonParser jp = new JsonParser();
        if (webResponse > 299) {
            return;
        }
        finish();
    }
}