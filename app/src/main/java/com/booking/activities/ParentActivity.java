package com.booking.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.booking.R;
import com.booking.fragments.ParentFragment;
import com.booking.interfaces.HttpResponse;

public class ParentActivity extends AppCompatActivity implements
        View.OnClickListener,
        HttpResponse {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    protected void replaceFragment(ParentFragment pf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, pf);
        ft.commit();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void webResponse(int code, int webResponse, String s) {

    }
}
