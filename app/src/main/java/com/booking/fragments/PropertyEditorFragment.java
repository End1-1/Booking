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
import com.booking.databinding.FragmentPropertyEditorBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoomOptions;
import com.booking.httpqueries.HttpHotelProperties;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;

public class PropertyEditorFragment extends ParentFragment {

    private FragmentPropertyEditorBinding bind;
    private String mGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = FragmentPropertyEditorBinding.inflate(inflater, container, false);
        bind.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.rv.setAdapter(new HotelPropertyAdapter());
        bind.back.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        bind.title.setText(b.getString("title"));
        bind.progress.setVisibility(View.VISIBLE);
        mGroup = b.getString("group");
        ((HotelPropertyAdapter) bind.rv.getAdapter()).mMultiSelection = b.getBoolean("multi");
        HttpHotelProperties hotelProperties = new HttpHotelProperties(Cnf.getString("object"), mGroup, this);
        hotelProperties.go();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
        }
    }

    @Override
    public boolean backPressed() {
        Dialog.alertDialog(getContext(), R.string.Empty, getString(R.string.ConfirmToSaveChanges), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            if (i == dialogInterface.BUTTON_POSITIVE) {
                bind.progress.setVisibility(View.VISIBLE);
                HttpSaveRoomOptions roomOptions = new HttpSaveRoomOptions(PropertyEditorFragment.this, mGroup);
                roomOptions.go();
            } else {
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(HotelFragment.class));
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
                ((HotelPropertyAdapter) bind.rv.getAdapter()).mProperties.clear();
                for (GRoomOptions r: GRoomOptions.mList) {
                    ((HotelPropertyAdapter) bind.rv.getAdapter()).mProperties.add(r);
                }
                bind.rv.getAdapter().notifyDataSetChanged();
                break;
            case HttpQueries.rcSaveRoomOptions:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(HotelFragment.class));
                break;
        }
    }
}