package com.booking.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.databinding.FragmentEditNameBinding;
import com.booking.databinding.FragmentHotelBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GObject;
import com.booking.gson.GObjectName;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveAccount;
import com.booking.utils.Cnf;
import com.booking.utils.Dialog;
import com.booking.utils.HttpQuery;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class EditNameFragment extends ParentFragment {

    private FragmentEditNameBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentEditNameBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.save.setOnClickListener(this);
        bind.progress.setVisibility(View.VISIBLE);
        HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/name.php", HttpQuery.mMethodPost, HttpQueries.rcNames);
        q.mWebResponse = this;
        q.setParameter("token", Cnf.getToken());
        q.setParameter("object", Cnf.getString("object"));
        q.request();
        return bind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
            case R.id.save:
                Dialog.alertDialog(getContext(), R.string.Empty, getString(R.string.ConfirmToSaveChanges), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        switch (i) {
                            case BUTTON_POSITIVE:
                                bind.progress.setVisibility(View.VISIBLE);
                                HttpQuery q = new HttpQuery(Cnf.mHttpHost + "/app/editname.php", HttpQuery.mMethodPost, HttpQueries.rcSaveName);
                                q.mWebResponse = EditNameFragment.this;
                                q.setParameter("token", Cnf.getToken());
                                q.setParameter("object", Cnf.getString("object"));
                                q.setParameter("am", bind.arm.getText().toString());
                                q.setParameter("en", bind.eng.getText().toString());
                                q.setParameter("ru", bind.rus.getText().toString());
                                q.request();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(HotelFragment.class));
        return false;
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        bind.progress.setVisibility(View.GONE);
        if (webResponse > 299) {
            Dialog.alertDialog(getContext(), R.string.Error, s);
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        if (ga.ok == 0) {
            Dialog.alertDialog(getContext(), R.string.Error, s);
            return;
        }
        switch (code) {
            case HttpQueries.rcNames:
                GObjectName n = GObjectName.parse(ga.data.get("names").getAsJsonObject(), GObjectName.class);
                bind.arm.setText(n.am);
                bind.eng.setText(n.en);
                bind.rus.setText(n.ru);
                break;
            case HttpQueries.rcSaveName:
                ((MainActivity) getActivity()).setObjectName(bind.arm.getText().toString());
                break;
        }
    }
}