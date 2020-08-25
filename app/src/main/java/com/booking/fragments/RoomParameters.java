package com.booking.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.adapters.BedAdapter;
import com.booking.adapters.HotelPropertyAdapter;
import com.booking.databinding.FragmentRoomParametersBinding;
import com.booking.gson.GAnswer;
import com.booking.httpqueries.HttpQueries;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.booking.utils.HttpQuery;
import com.google.gson.JsonArray;

public class RoomParameters extends ParentFragment {

    private FragmentRoomParametersBinding bind;
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
        bind = FragmentRoomParametersBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.rvBedOneRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvBedOneRoom.setAdapter(new BedAdapter());
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/roomparameter.php", HttpQuery.mMethodPost, HttpQueries.rcRoomParameter);
        q.mWebResponse = this;
        q.setParameter("token", Cnf.getToken());
        q.setParameter("room", mId);
        q.request();
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
                    HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/roomparametersave.php", HttpQuery.mMethodPost, HttpQueries.rcSaveRoomParameter);
                    q.mWebResponse = RoomParameters.this;
                    q.setParameter("token", Cnf.getToken());
                    q.setParameter("room", mId);
                    q.setParameter("area", bind.area.getText().toString());
                    q.setParameter("oneroom", bind.oneroomswitch.isChecked() ? "1" : "0");
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvBedOneRoom.getAdapter()).mList) {
                        q.setParameter(String.format("bedoneroom[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
//                    q.setParameter("group", "12");
//                    q.setParameter("options", ((HotelPropertyAdapter) bind.rv.getAdapter()).checkedList("12"));
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
            case HttpQueries.rcRoomParameter:
                bind.area.setText(String.valueOf(ga.data.get("area").getAsInt()));
                JsonArray jaRoomCount = ga.data.get("roomcount").getAsJsonArray();
                setOneRoom();
                for (int i = 0; i < jaRoomCount.size(); i++) {
                    if (jaRoomCount.get(i).getAsInt() == 83) {
                        setMultiRoom();
                    }
                }
                BedAdapter.GBed.from(ga.data.get("bedoneroom").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvBedOneRoom.getAdapter()).mList);
                bind.rvBedOneRoom.getAdapter().notifyDataSetChanged();
                break;
            case HttpQueries.rcSaveRoomParameter:
                openRoom();
                break;
        }
    }

    private void openRoom() {
        Bundle b = new Bundle();
        b.putString("room", mId);
        RoomPropertyFragment rpf = new RoomPropertyFragment();
        rpf.setArguments(b);
        ((MainActivity) getActivity()).replaceFragment(rpf);
    }

    private void setOneRoom() {
        bind.oneroomswitch.setChecked(true);
    }

    private void setMultiRoom() {
        bind.oneroomswitch.setChecked(false);
        bind.rvBedOneRoom.setVisibility(View.GONE);
    }
}