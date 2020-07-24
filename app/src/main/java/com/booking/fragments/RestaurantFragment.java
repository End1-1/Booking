package com.booking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.databinding.FragmentRestaurantBinding;

public class RestaurantFragment extends ParentFragment {

    private FragmentRestaurantBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentRestaurantBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }
}