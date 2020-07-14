package com.booking.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.booking.R;
import com.booking.fragments.AutologinFragment;
import com.booking.fragments.BlankFragment;
import com.booking.fragments.LoginFragment;
import com.booking.fragments.ParentFragment;
import com.booking.utils.Cnf;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void login() {
        Log.d("MAIN ACTIVITY", "LOGIN");
        replaceFragment(ParentFragment.newInstance(BlankFragment.class));
    }

    public void autologin() {
        Log.d("MAIN ACTIVITY", "AUTO LOGIN");
        replaceFragment(ParentFragment.newInstance(BlankFragment.class));
    }

    public void standartLogin() {
        replaceFragment(ParentFragment.newInstance(LoginFragment.class));
    }
}