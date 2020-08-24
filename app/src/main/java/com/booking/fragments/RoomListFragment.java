package com.booking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.adapters.RoomsAdapter;
import com.booking.databinding.FragmentRoomListBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoom;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpRooms;
import com.booking.utils.Dialog;
import com.google.gson.JsonArray;

public class RoomListFragment extends ParentFragment {

    private FragmentRoomListBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentRoomListBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rv.setAdapter(new RoomsAdapter(getActivity()));
        ((RoomsAdapter) bind.rv.getAdapter()).mOpenProperty = true;
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        bind.progress.setVisibility(View.VISIBLE);
        HttpRooms rooms = new HttpRooms(this);
        rooms.go();
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        bind.progress.setVisibility(View.GONE);
        if (webResponse > 299) {
            Dialog.alertDialog(getContext(), R.string.Error, s);
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
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
                bind.rv.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(HotelFragment.class));
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
        }
    }
}