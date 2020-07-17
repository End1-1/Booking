package com.booking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentLoginBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GUser;
import com.booking.httpqueries.HttpLogin;
import com.booking.httpqueries.HttpQueries;
import com.booking.interfaces.HttpResponse;
import com.booking.utils.Cnf;

public class LoginFragment extends ParentFragment {

    private FragmentLoginBinding mBind;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBind = FragmentLoginBinding.inflate(inflater, container, false);
        mBind.login.setOnClickListener(this);
        return mBind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                mBind.loading.setVisibility(View.VISIBLE);
                mBind.login.setEnabled(false);
                mBind.message.setVisibility(View.GONE);
                HttpLogin httpLogin =  new HttpLogin(mBind.username.getText().toString(), mBind.password.getText().toString(), this);
                httpLogin.go();
                break;
        }
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.loading.setVisibility(View.GONE);
        mBind.login.setEnabled(true);
        if (webResponse > 299) {
            mBind.message.setVisibility(View.VISIBLE);
            mBind.message.setText(s);
            return;
        }
        GAnswer ga = GAnswer.parse(s);
        if (ga == null) {
            return;
        }
        if (webResponse > 299) {
            return;
        }
        if (ga.ok == 0) {
            mBind.message.setVisibility(View.VISIBLE);
            mBind.message.setText(ga.msg);
            return;
        }
        switch (code) {
            case HttpQueries.rcAuth:
                GUser gu = GUser.parse(ga.data);
                Cnf.setInt("user_id", gu.id);
                Cnf.setString("firstname", gu.firstname);
                Cnf.setString("lastname", gu.lastname);
                Cnf.setString("email", gu.email);
                Cnf.setString("phone", gu.phone);
                ((MainActivity) getActivity()).login();
                break;
        }
    }
}