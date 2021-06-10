package com.quwan.tt.asmdemoapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

public class TestService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("====>TestService:", "onCreate");
        startForeground(0, new Notification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
