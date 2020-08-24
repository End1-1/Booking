package com.booking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentRoomPropertyBinding;
import com.booking.gson.GAnswer;
import com.booking.utils.Dialog;

public class RoomPropertyFragment extends ParentFragment {

    private FragmentRoomPropertyBinding bind;
    private String mId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentRoomPropertyBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.editClass.setOnClickListener(this);
        bind.editFacilities.setOnClickListener(this);
        bind.editParameters.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        if (b != null) {
            mId = b.getString("room");
        }
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
    }

    @Override
    public void onClick(View view) {
        Bundle ba = new Bundle();
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
            case R.id.editClass:
                ba.putString("group", "12");
                ba.putString("title", getString(R.string.Class));
                ba.putBoolean("multi", false);
                ba.putString("room", mId);
                RoomClass pclass = ParentFragment.newInstance(RoomClass.class);
                pclass.setArguments(ba);
                ((MainActivity) getActivity()).replaceFragment(pclass);
                break;
            case R.id.editFacilities:
                ba.putString("group", "10");
                ba.putString("title", getString(R.string.Facilities));
                ba.putBoolean("multi", true);
                ba.putString("room", mId);
                PropertyEditorFragment pfac = ParentFragment.newInstance(PropertyEditorFragment.class);
                pfac.setArguments(ba);
                ((MainActivity) getActivity()).replaceFragment(pfac);
                break;
            case R.id.editParameters:
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(RoomListFragment.class));
        return false;
    }
}