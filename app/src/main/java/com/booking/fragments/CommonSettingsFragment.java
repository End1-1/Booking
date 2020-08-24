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
import com.booking.databinding.FragmentCommonSettingsBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoomOptions;
import com.booking.httpqueries.HttpHotelProperties;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.google.gson.JsonArray;

public class CommonSettingsFragment extends ParentFragment {

    private FragmentCommonSettingsBinding bind;
    private String rvNext = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCommonSettingsBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.rv5.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rv8.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rv5.setAdapter(new HotelPropertyAdapter());
        bind.rv8.setAdapter(new HotelPropertyAdapter());
        HttpHotelProperties roomOptions = new HttpHotelProperties(Cnf.getString("object"), "",this);
        roomOptions.go();
        bind.progress.setVisibility(View.VISIBLE);
        return bind.getRoot();
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
                            HttpSaveRoomOptions roomOptions = new HttpSaveRoomOptions(CommonSettingsFragment.this, "5");
                            rvNext = "8";
                            roomOptions.go();
                        } else {
                            ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
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
            case HttpQueries.rcRoomOptions:
                GRoomOptions.from(ga.data.get("elements").getAsJsonArray(), GRoomOptions.class, GRoomOptions.mList);
                for (GRoomOptions r: GRoomOptions.mList) {
                    switch (Integer.valueOf(r.fparamtype)) {
                        case 5:
                            ((HotelPropertyAdapter) bind.rv5.getAdapter()).mProperties.add(r);
                            break;
                        case 8:
                            ((HotelPropertyAdapter) bind.rv8.getAdapter()).mProperties.add(r);
                            break;
                    }
                }
                bind.rv5.getAdapter().notifyDataSetChanged();
                bind.rv8.getAdapter().notifyDataSetChanged();
                break;
            case HttpQueries.rcSaveRoomOptions:
                if (rvNext.equals("8")) {
                    bind.progress.setVisibility(View.VISIBLE);
                    rvNext = "999";
                    HttpSaveRoomOptions roomOptions = new HttpSaveRoomOptions(this, "8");
                    roomOptions.go();
                    break;
                }
                if (rvNext.equals("999")) {
                    ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
                }
                break;
        }
    }
}