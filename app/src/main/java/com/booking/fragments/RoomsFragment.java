package com.booking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.booking.R;
import com.booking.activities.App;
import com.booking.activities.RoomEditActivity;
import com.booking.activities.RoomOptionsActivity;
import com.booking.adapters.RoomsAdapter;
import com.booking.databinding.FragmentRoomsBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoom;
import com.booking.gson.GRoomCategory;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpRoomCategory;
import com.booking.httpqueries.HttpRooms;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class RoomsFragment extends ParentFragment {

    private FragmentRoomsBinding mBind;
    private RoomsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new RoomsAdapter(getActivity());
        mBind = FragmentRoomsBinding.inflate(inflater, container, false);
        mBind.rvData.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvData.setAdapter(mAdapter);
        mBind.roomoptions.setOnClickListener(this);
        mBind.newroom.setOnClickListener(this);
        return mBind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpRooms httpRooms = new HttpRooms(this);
        httpRooms.go();
        if (GRoomCategory.mList.isEmpty()) {
            HttpRoomCategory roomCategory = new HttpRoomCategory(this);
            roomCategory.go();
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        if (webResponse > 299) {
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        if (ga == null) {
            return;
        }
        if (ga.ok == 0) {
            return;
        }
        switch (code) {
            case HttpQueries.rcRooms:
                GRoom.mList.clear();
                JsonArray ja = ga.data.getAsJsonArray("rooms");
                for (int i = 0; i < ja.size(); i++) {
                    GRoom r = GRoom.parse(ja.get(i).getAsJsonObject(), GRoom.class);
                    GRoom.mList.add(r);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case HttpQueries.rcRoomCategoryList:
                JsonArray jacat = ga.data.getAsJsonArray("categories");
                GRoomCategory.from(jacat, GRoomCategory.class, GRoomCategory.mList);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.roomoptions:
                Intent rointent = new Intent(getContext(), RoomOptionsActivity.class);
                startActivity(rointent);
                break;
            case R.id.newroom:
                Intent intent = new Intent(App.context(), RoomEditActivity.class);
                intent.putExtra("room", "0");
                intent.putExtra("name", getString(R.string.NewRoom));
                intent.putExtra("pax", "1");
                intent.putExtra("price", "0");
                startActivity(intent);
                break;
        }
    }
}