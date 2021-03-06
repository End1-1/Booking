package com.booking.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.adapters.HotelPropertyAdapter;
import com.booking.adapters.RoomOptionAdapter;
import com.booking.databinding.FragmentRoomClassBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoomOptions;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.booking.utils.HttpQuery;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RoomClass extends ParentFragment {

    private FragmentRoomClassBinding bind;
    private String mId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString("room");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentRoomClassBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rv.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rv.getAdapter()).mMultiSelection = false;
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpQueries q = new HttpQueries(Cnf.mHttpHost + "/app/roomclass.php", HttpQuery.mMethodPost, HttpQueries.rcRoomClass);
        q.mQuery.mWebResponse = this;
        q.setParameter("room", mId);
        q.setParameter("group", "12");
        q.go();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
        }
    }

    @Override
    public boolean backPressed() {
        Dialog.alertDialog(getContext(), R.string.Empty, getString(R.string.ConfirmToSaveChanges), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == dialogInterface.BUTTON_POSITIVE) {
                    bind.progress.setVisibility(View.VISIBLE);
                    HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/roomclasssave.php", HttpQuery.mMethodPost, HttpQueries.rcSaveRoomClass);
                    q.mWebResponse = RoomClass.this;
                    q.setParameter("token", Cnf.getToken());
                    q.setParameter("room", mId);
                    q.setParameter("group", "12");
                    q.setParameter("options", ((HotelPropertyAdapter) bind.rv.getAdapter()).checkedList("12"));
                    q.request();
                } else {
                    openRoom();
                }
            }
        });
        return false;
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
            case HttpQueries.rcRoomClass:
                GRoomOptions.from(ga.data.get("elements").getAsJsonArray(), GRoomOptions.class, GRoomOptions.mList);
                ((HotelPropertyAdapter) bind.rv.getAdapter()).mProperties = GRoomOptions.mList;
                bind.rv.getAdapter().notifyDataSetChanged();
                break;
            case HttpQueries.rcSaveRoomClass:
                openRoom();
                break;
        }
    }

    void openRoom() {
        Bundle b = new Bundle();
        b.putString("room", mId);
        RoomPropertyFragment rpf = new RoomPropertyFragment();
        rpf.setArguments(b);
        ((MainActivity) getActivity()).replaceFragment(rpf);
    }
}