package com.booking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentAutologinBinding;
import com.booking.gson.GAnswer;
import com.booking.httpqueries.HttpAutologin;
import com.booking.interfaces.HttpResponse;

public class AutologinFragment extends ParentFragment implements HttpResponse {

    private FragmentAutologinBinding mBind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBind = FragmentAutologinBinding.inflate(inflater, container, false);
        mBind.retry.setOnClickListener(this);
        mBind.login.setOnClickListener(this);
        return mBind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpAutologin httpAutologin = new HttpAutologin(this);
        httpAutologin.go();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry:
                mBind.retry.setVisibility(View.GONE);
                mBind.login.setVisibility(View.GONE);
                break;
            case R.id.login:
                ((MainActivity) getActivity()).standartLogin();
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        if (webResponse > 299) {
            mBind.retry.setVisibility(View.VISIBLE);
            mBind.login.setVisibility(View.VISIBLE);
            return;
        }
        GAnswer ga = GAnswer.parse(s);
        if (ga == null) {
            mBind.retry.setVisibility(View.VISIBLE);
            mBind.login.setVisibility(View.VISIBLE);
            return;
        }
        if (ga.ok == 0) {
            mBind.retry.setVisibility(View.VISIBLE);
            mBind.login.setVisibility(View.VISIBLE);
            return;
        }
        ((MainActivity) getActivity()).autologin();
    }
}