package com.booking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentCommonSettingsBinding;

public class CommonSettingsFragment extends ParentFragment {

    private FragmentCommonSettingsBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCommonSettingsBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
        return false;
    }
}