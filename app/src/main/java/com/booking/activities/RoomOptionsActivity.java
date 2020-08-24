package com.booking.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.booking.R;
import com.booking.adapters.RoomOptionAdapter;
import com.booking.databinding.ActivityRoomOptionsBinding;
import com.booking.gson.GRoomOptions;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpHotelProperties;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class RoomOptionsActivity extends ParentActivity {

    private ActivityRoomOptionsBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRoomOptionsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.rvCheck.setLayoutManager(new LinearLayoutManager(this));
        bind.rvCheck.setAdapter(new RoomOptionAdapter());
        bind.name.setText(Cnf.getString("name"));
        bind.progress.setVisibility(View.VISIBLE);
        HttpHotelProperties ro = new HttpHotelProperties(Cnf.getString("object"), "", this);
        ro.go();
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        bind.progress.setVisibility(View.GONE);
        JsonParser jp = new JsonParser();
        if (webResponse > 299) {
            Dialog.alertDialog(this, R.string.Error, s);
            return;
        }
        switch (code) {
            case HttpQueries.rcRoomOptions:
                JsonArray ja = jp.parse(s).getAsJsonObject().get("data").getAsJsonObject().get("elements").getAsJsonArray();
                GRoomOptions.from(ja, GRoomOptions.class, GRoomOptions.mList);
                bind.rvCheck.getAdapter().notifyDataSetChanged();
                break;
            case HttpQueries.rcSaveRoomOptions:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Dialog.alertDialog(this, R.string.SaveChanges, getString(R.string.SaveChanges), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                switch (i) {
                    case BUTTON_POSITIVE:
                        dialog.dismiss();
                        bind.progress.setVisibility(View.VISIBLE);
                        HttpSaveRoomOptions ro = new HttpSaveRoomOptions(RoomOptionsActivity.this, "");
                        ro.go();
                        break;
                    default:
                        RoomOptionsActivity.super.onBackPressed();
                        break;
                }
            }
        });
    }
}