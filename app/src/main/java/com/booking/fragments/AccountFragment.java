package com.booking.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booking.R;
import com.booking.activities.MainActivity;
import com.booking.activities.RoomOptionsActivity;
import com.booking.databinding.FragmentAccountBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GUser;
import com.booking.httpqueries.HttpAccount;
import com.booking.httpqueries.HttpChangePassword;
import com.booking.httpqueries.HttpQueries;
import com.booking.httpqueries.HttpSaveAccount;
import com.booking.httpqueries.HttpSaveRoomOptions;
import com.booking.utils.Dialog;

import java.nio.channels.GatheringByteChannel;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class AccountFragment extends ParentFragment {

    private FragmentAccountBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentAccountBinding.inflate(inflater, container, false);
        bind.back.setOnClickListener(this);
        bind.changePassword.setOnClickListener(this);
        bind.save.setOnClickListener(this);
        bind.progress.setVisibility(View.VISIBLE);
        HttpAccount account = new HttpAccount(this);
        account.go();
        return bind.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
                break;
            case R.id.changePassword:
                changePassword();
                break;
            case R.id.save:
                Dialog.alertDialog(getContext(), R.string.Empty, getString(R.string.ConfirmToSaveChanges), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        switch (i) {
                            case BUTTON_POSITIVE:
                                bind.progress.setVisibility(View.VISIBLE);
                                HttpSaveAccount saveAccount = new HttpSaveAccount(bind.accFirstname.getText().toString(), bind.accLastname.getText().toString(), bind.accEmail.getText().toString(), bind.accPhone.getText().toString(), AccountFragment.this);
                                saveAccount.go();
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
    public void webResponse(int code, int webResponse, String s) {
        bind.progress.setVisibility(View.GONE);
        if (webResponse > 299) {
            Dialog.alertDialog(getContext(), R.string.Error, s);
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        switch (code) {
            case HttpQueries.rcChangePassword:
                if (ga.ok == 0) {
                    Dialog.alertDialog(getContext(), R.string.Error, R.string.YourCurrentPasswordIsInvalid);
                } else {
                    Dialog.alertDialog(getContext(), R.string.Empty, R.string.YourPasswordWasChanged);
                }
                break;
            case HttpQueries.rcAccount:
                GUser u = GUser.parse(ga.data, GUser.class);
                bind.accFirstname.setText(u.firstname);
                bind.accLastname.setText(u.lastname);
                bind.accEmail.setText(u.email);
                bind.accPhone.setText(u.phone);
                bind.accID.setText(u.id);
                break;
        }
    }

    @Override
    public boolean backPressed() {
        ((MainActivity) getActivity()).replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
        return false;
    }

    private void changePassword() {
        if (bind.newPassword.getText().toString().isEmpty()) {
            Dialog.alertDialog(getContext(), R.string.Error, R.string.PasswordCannotBeEmtpy);
            return;
        }
        if (!bind.newPassword.getText().toString().equals(bind.repeatPassword.getText().toString())) {
            Dialog.alertDialog(getContext(), R.string.Error, R.string.PasswordsIsNotEqual);
            return;
        }
        bind.progress.setVisibility(View.VISIBLE);
        HttpChangePassword changePassword = new HttpChangePassword(bind.currentPassword.getText().toString(), bind.newPassword.getText().toString(), this);
        changePassword.go();
    }
}