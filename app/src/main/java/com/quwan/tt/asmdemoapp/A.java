package com.quwan.tt.asmdemoapp;

import android.os.AsyncTask;

public class A {
    void test(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        }.execute();
        System.out.println("====>AsyncTask");
    }
}
