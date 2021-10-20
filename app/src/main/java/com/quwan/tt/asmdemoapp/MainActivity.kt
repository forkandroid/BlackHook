package com.quwan.tt.asmdemoapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread()
    }
}