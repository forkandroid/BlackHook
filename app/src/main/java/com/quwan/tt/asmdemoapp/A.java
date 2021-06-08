package com.quwan.tt.asmdemoapp;

import android.os.AsyncTask;

public class A {
    void test(){
        new ThreadCheck().printThread("sAS");
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        }.execute();
        System.out.println("====>AsyncTask");
    }
}
