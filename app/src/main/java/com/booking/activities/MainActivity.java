package com.booking.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.booking.R;
import com.booking.databinding.ActivityMainBinding;
import com.booking.fragments.AutologinFragment;
import com.booking.fragments.BlankFragment;
import com.booking.fragments.BookingsFragment;
import com.booking.fragments.LoginFragment;
import com.booking.fragments.ParentFragment;
import com.booking.fragments.RestaurantFragment;
import com.booking.fragments.RoomsFragment;
import com.booking.fragments.SettingsFragment;
import com.booking.utils.Cnf;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends ParentActivity {

    private ActivityMainBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        mBind.buttons.setVisibility(View.GONE);
        mBind.bookings.setOnClickListener(this);
        mBind.rooms.setOnClickListener(this);
        mBind.arrives.setOnClickListener(this);
        mBind.departures.setOnClickListener(this);
        mBind.kitchen.setOnClickListener(this);
        mBind.checkin.setOnClickListener(this);
        mBind.settings.setOnClickListener(this);
        mBind.menu.setOnClickListener(this);
        mBind.history.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.d("OnComplete error", task.getException().getMessage());
                    return;
                }
                Cnf.setToken(task.getResult().getToken());
            }
        });

        if (Cnf.getString("user_id").isEmpty()) {
            Cnf.setString("user_id", "0");
        }
        if (Integer.valueOf(Cnf.getString("user_id")) == 0) {
            replaceFragment(ParentFragment.newInstance(LoginFragment.class));
        } else {
            replaceFragment(ParentFragment.newInstance(AutologinFragment.class));
        }
    }

    @Override
    public void onBackPressed() {
        ParentFragment f = fragment();
        if (f != null) {
            if (!f.backPressed()) {
                return;
            }
        }
        if (mBind.flMenu.getVisibility() == View.VISIBLE) {
            hideMenu();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                if (mBind.flMenu.getVisibility() == View.GONE) {
                    mBind.flMenu.setVisibility(View.VISIBLE);
                    mBind.flMenu.setAlpha(0.0f);
                    mBind.flMenu.animate()
                            .alpha(1.0f)
                            .setListener(null);
                } else {
                    hideMenu();
                }
                break;
            case R.id.bookings:
                hideMenu();
                replaceFragment(ParentFragment.newInstance(BookingsFragment.class));
                break;
            case R.id.rooms:
                hideMenu();
                replaceFragment(ParentFragment.newInstance(RoomsFragment.class));
                break;
            case R.id.arrives:
                hideMenu();
                Bundle ba = new Bundle();
                ba.putBoolean("arrives", true);
                BookingsFragment bfa = ParentFragment.newInstance(BookingsFragment.class);
                bfa.setArguments(ba);
                replaceFragment(bfa);
                break;
            case R.id.checkin:
                hideMenu();
                Bundle bc = new Bundle();
                bc.putBoolean("checkin", true);
                BookingsFragment bfc = ParentFragment.newInstance(BookingsFragment.class);
                bfc.setArguments(bc);
                replaceFragment(bfc);
                break;
            case R.id.departures:
                hideMenu();
                Bundle bd = new Bundle();
                bd.putBoolean("departures", true);
                BookingsFragment bfd = ParentFragment.newInstance(BookingsFragment.class);
                bfd.setArguments(bd);
                replaceFragment(bfd);
                break;
            case R.id.kitchen:
                hideMenu();
                replaceFragment(ParentFragment.newInstance(RestaurantFragment.class));
                break;
            case R.id.settings:
                hideMenu();
                replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
                break;
            case R.id.history:
                hideMenu();
                break;
        }
    }

    public void login() {
        replaceFragment(ParentFragment.newInstance(BlankFragment.class));
        startWork();
    }

    public void autologin() {
        startWork();
    }

    public void standartLogin() {
        replaceFragment(ParentFragment.newInstance(LoginFragment.class));
    }

    public void setObjectName(String name) {
        Cnf.setString("name", name);
        mBind.name.setText(Cnf.getString("name"));
    }

    void startWork() {
        mBind.buttons.setVisibility(View.VISIBLE);
        mBind.name.setText(Cnf.getString("name"));
        replaceFragment(ParentFragment.newInstance(BookingsFragment.class));
    }

    void hideMenu() {
        mBind.flMenu.animate()
                .translationY(0)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mBind.flMenu.setVisibility(View.GONE);
                    }
                });
    }
}