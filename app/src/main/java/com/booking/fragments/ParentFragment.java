package com.booking.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.booking.interfaces.HttpResponse;

public class ParentFragment extends Fragment implements View.OnClickListener,
        HttpResponse {

    public ParentFragment() {

    }

    public static <T> T newInstance(Class<T> type) {
        try {
            T t = type.newInstance();
            return t;
        } catch (java.lang.IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void webResponse(int code, int webResponse, String s) {

    }
}
