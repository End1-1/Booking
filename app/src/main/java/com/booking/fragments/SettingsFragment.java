package com.booking.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentSettingsBinding;
import com.booking.utils.AnimateView;

public class SettingsFragment extends ParentFragment {

    private FragmentSettingsBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSettingsBinding.inflate(inflater, container, false);
        bind.account.setOnClickListener(this);
        bind.generalSettings.setOnClickListener(this);
        bind.stays.setOnClickListener(this);
        bind.holidayPark.setOnClickListener(this);
        bind.restaurant.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(AccountFragment.class));
                break;
            case R.id.generalSettings:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(CommonSettingsFragment.class));
                break;
        }
    }
}