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
        bind.oneroomswitch.setOnClickListener(this);
        bind.multiroomswitch.setOnClickListener(this);
        bind.rvBedOneRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvBedOneRoom.setAdapter(new BedAdapter());
        bind.bedroom1switch.setOnClickListener(this);
        bind.bedroom2switch.setOnClickListener(this);
        bind.rvExtraBedOnRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvExtraBedOnRoom.setAdapter(new BedAdapter());
        bind.rvOneRoomUponRequest.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvOneRoomUponRequest.setAdapter(new BedAdapter());
        bind.rvMultiSleep1.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvMultiSleep1.setAdapter(new BedAdapter());
        bind.rvExtraBedM1.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvExtraBedM1.setAdapter(new BedAdapter());
        bind.rvUponRequestM1.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvUponRequestM1.setAdapter(new BedAdapter());
        bind.rvMultiSleep2.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvMultiSleep2.setAdapter(new BedAdapter());
        bind.rvExtraBedM2.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvExtraBedM2.setAdapter(new BedAdapter());
        bind.rvUponRequestM2.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvUponRequestM2.setAdapter(new BedAdapter());
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
            case R.id.oneroomswitch:
                if (bind.oneroomswitch.isChecked()) {
                    setOneRoom();
                } else {
                    setMultiRoom();
                }
                break;
            case R.id.multiroomswitch:
                if (bind.multiroomswitch.isChecked()) {
                    setMultiRoom();
                } else {
                    setOneRoom();
                }
                break;
            case R.id.bedroom1switch:
                bind.lbedroom1.setVisibility(bind.bedroom1switch.isChecked() ? View.VISIBLE : View.GONE);
                break;
            case R.id.bedroom2switch:
                bind.lbedroom2.setVisibility(bind.bedroom2switch.isChecked() ? View.VISIBLE : View.GONE);
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
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvExtraBedOnRoom.getAdapter()).mList) {
                        q.setParameter(String.format("extrabedoneroom[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvOneRoomUponRequest.getAdapter()).mList) {
                        q.setParameter(String.format("uponrequest[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvMultiSleep1.getAdapter()).mList) {
                        q.setParameter(String.format("bedmultiroom[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvExtraBedM1.getAdapter()).mList) {
                        q.setParameter(String.format("extram1[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvUponRequestM1.getAdapter()).mList) {
                        q.setParameter(String.format("uponm1[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvMultiSleep2.getAdapter()).mList) {
                        q.setParameter(String.format("bedmultiroom2[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvExtraBedM2.getAdapter()).mList) {
                        q.setParameter(String.format("extram2[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
                    for (BedAdapter.GBed b: ((BedAdapter) bind.rvUponRequestM2.getAdapter()).mList) {
                        q.setParameter(String.format("uponm2[%s]", b.id), b.qty == null ? "0" : b.qty);
                    }
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
                BedAdapter.GBed.from(ga.data.get("extrabedoneroom").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvExtraBedOnRoom.getAdapter()).mList);
                bind.rvExtraBedOnRoom.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("uponrequest").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvOneRoomUponRequest.getAdapter()).mList);
                bind.rvOneRoomUponRequest.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("bedmultiroom").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvMultiSleep1.getAdapter()).mList);
                bind.rvMultiSleep1.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("extram1").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvExtraBedM1.getAdapter()).mList);
                bind.rvExtraBedM1.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("uponm1").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvUponRequestM1.getAdapter()).mList);
                bind.rvUponRequestM1.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("bedmultiroom2").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvMultiSleep2.getAdapter()).mList);
                bind.rvMultiSleep2.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("extram2").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvExtraBedM2.getAdapter()).mList);
                bind.rvExtraBedM2.getAdapter().notifyDataSetChanged();
                BedAdapter.GBed.from(ga.data.get("uponm2").getAsJsonArray(), BedAdapter.GBed.class, ((BedAdapter) bind.rvUponRequestM2.getAdapter()).mList);
                bind.rvUponRequestM2.getAdapter().notifyDataSetChanged();
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
        bind.multiroomswitch.setChecked(false);
        bind.loneroom.setVisibility(View.VISIBLE);
        bind.lmultiroom.setVisibility(View.GONE);
    }

    private void setMultiRoom() {
        bind.oneroomswitch.setChecked(false);
        bind.multiroomswitch.setChecked(true);
        bind.loneroom.setVisibility(View.GONE);
        bind.lmultiroom.setVisibility(View.VISIBLE);
        bind.bedroom1switch.setChecked(false);
        bind.bedroom2switch.setChecked(false);
        bind.lbedroom1.setVisibility(bind.bedroom1switch.isChecked() ? View.VISIBLE : View.GONE);
        bind.lbedroom2.setVisibility(bind.bedroom1switch.isChecked() ? View.VISIBLE : View.GONE);
    }
}