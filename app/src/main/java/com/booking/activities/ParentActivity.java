package com.booking.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.booking.R;
import com.booking.databinding.FragmentCommonSettingsBinding;
import com.booking.fragments.ParentFragment;
import com.booking.interfaces.HttpResponse;

import java.util.List;

public class ParentActivity extends AppCompatActivity implements
        View.OnClickListener,
        HttpResponse {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    public void replaceFragment(ParentFragment pf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, pf);
        ft.commit();
    }

    public ParentFragment fragment() {
        List<Fragment> l = getSupportFragmentManager().getFragments();
        if (l != null) {
            for (Fragment f: l) {
                if (f != null && f.isVisible()) {
                    return (ParentFragment) f;
                }
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void webResponse(int code, int webResponse, String s) {

    }
}
