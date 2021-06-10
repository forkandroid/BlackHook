package com.quwan.tt.asmdemoapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class A {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    void test(){

//        Service service = null;
//        service.startForeground(0, new Notification());

        BaseService baseService = new BaseService() {
            @Nullable
            @Override
            public IBinder onBind(Intent intent) {
                return null;
            }
        };

        baseService.startForegroundIfNeed(0,new Notification(),true);

//        new ThreadCheck().printThread("sAS");
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                return null;
//            }
//        }.execute();
//        System.out.println("====>AsyncTask");
    }
}
