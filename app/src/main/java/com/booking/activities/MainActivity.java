package com.booking.activities;

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

        if (Cnf.getInt("user_id") == 0) {
            replaceFragment(ParentFragment.newInstance(LoginFragment.class));
        } else {
            replaceFragment(ParentFragment.newInstance(AutologinFragment.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookings:
                replaceFragment(ParentFragment.newInstance(BookingsFragment.class));
                break;
            case R.id.rooms:
                replaceFragment(ParentFragment.newInstance(RoomsFragment.class));
                break;
            case R.id.arrives:
                Bundle ba = new Bundle();
                ba.putBoolean("arrives", true);
                BookingsFragment bfa = ParentFragment.newInstance(BookingsFragment.class);
                bfa.setArguments(ba);
                replaceFragment(bfa);
                break;
            case R.id.checkin:
                Bundle bc = new Bundle();
                bc.putBoolean("checkin", true);
                BookingsFragment bfc = ParentFragment.newInstance(BookingsFragment.class);
                bfc.setArguments(bc);
                replaceFragment(bfc);
                break;
            case R.id.departures:
                Bundle bd = new Bundle();
                bd.putBoolean("departures", true);
                BookingsFragment bfd = ParentFragment.newInstance(BookingsFragment.class);
                bfd.setArguments(bd);
                replaceFragment(bfd);
                break;
            case R.id.kitchen:
                replaceFragment(ParentFragment.newInstance(RestaurantFragment.class));
                break;
            case R.id.settings:
                replaceFragment(ParentFragment.newInstance(SettingsFragment.class));
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

    void startWork() {
        mBind.buttons.setVisibility(View.VISIBLE);
        replaceFragment(ParentFragment.newInstance(BookingsFragment.class));
    }
}