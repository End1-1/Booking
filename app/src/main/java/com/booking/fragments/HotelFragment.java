package com.booking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentHotelBinding;
import com.booking.databinding.FragmentSettingsBinding;

public class HotelFragment extends ParentFragment {

    private FragmentHotelBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentHotelBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.editName.setOnClickListener(this);
        bind.generalSettings.setOnClickListener(this);
        bind.editRooms.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
            case R.id.editName:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(EditNameFragment.class));
                break;
            case R.id.editRooms:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(RoomListFragment.class));
                break;
            case R.id.generalSettings:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(CommonSettingsFragment.class));
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
        return false;
    }
}