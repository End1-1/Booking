package com.booking.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.booking.R;
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
        bind.back1.setOnClickListener(this);
        return bind.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                AnimateView.animate(bind.pageAccount, null);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back1:
                AnimateView.animate(bind.pageAccount, bind.main);
                break;
            case R.id.account:
                AnimateView.animate(bind.main, bind.pageAccount);
                break;
        }
    }
}