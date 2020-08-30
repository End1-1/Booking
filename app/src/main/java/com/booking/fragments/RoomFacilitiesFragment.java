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
import com.booking.databinding.FragmentRoomFacilitiesBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoomOptions;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.booking.utils.HttpQuery;

public class RoomFacilitiesFragment extends ParentFragment {

    private FragmentRoomFacilitiesBinding bind;
    private String mId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentRoomFacilitiesBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.rvspecial.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvspecial.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rvspecial.getAdapter()).mMultiSelection = false;
        bind.rvplacenumber.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvplacenumber.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rvplacenumber.getAdapter()).mMultiSelection = false;
        bind.rvfacilities.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvfacilities.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rvfacilities.getAdapter()).mMultiSelection = false;
        bind.rvmainname.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvmainname.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rvmainname.getAdapter()).mMultiSelection = false;
        bind.rvview.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rvview.setAdapter(new HotelPropertyAdapter());
        ((HotelPropertyAdapter) bind.rvview.getAdapter()).mMultiSelection = false;
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        mId = b.getString("room");
        HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/roomfacilities.php", HttpQuery.mMethodPost, HttpQueries.rcRoomParameter);
        q.mWebResponse = this;
        q.setParameter("token", Cnf.getToken());
        q.setParameter("room", mId);
        q.request();
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
        GRoomOptions.from(ga.data.get("17").getAsJsonArray(), GRoomOptions.class, ((HotelPropertyAdapter) bind.rvspecial.getAdapter()).mProperties);
        bind.rvspecial.getAdapter().notifyDataSetChanged();
        GRoomOptions.from(ga.data.get("18").getAsJsonArray(), GRoomOptions.class, ((HotelPropertyAdapter) bind.rvplacenumber.getAdapter()).mProperties);
        bind.rvplacenumber.getAdapter().notifyDataSetChanged();
        GRoomOptions.from(ga.data.get("19").getAsJsonArray(), GRoomOptions.class, ((HotelPropertyAdapter) bind.rvmainname.getAdapter()).mProperties);
        bind.rvmainname.getAdapter().notifyDataSetChanged();
        GRoomOptions.from(ga.data.get("20").getAsJsonArray(), GRoomOptions.class, ((HotelPropertyAdapter) bind.rvfacilities.getAdapter()).mProperties);
        bind.rvfacilities.getAdapter().notifyDataSetChanged();
        GRoomOptions.from(ga.data.get("21").getAsJsonArray(), GRoomOptions.class, ((HotelPropertyAdapter) bind.rvview.getAdapter()).mProperties);
        bind.rvview.getAdapter().notifyDataSetChanged();
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
                    ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(RoomPropertyFragment.class));
//                    HttpSaveRoomOptions roomOptions = new HttpSaveRoomOptions(PropertyEditorFragment.this, mGroup);
//                    roomOptions.go();
                } else {
                    ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(RoomPropertyFragment.class));
                }
            }
        });
        return false;
    }
}