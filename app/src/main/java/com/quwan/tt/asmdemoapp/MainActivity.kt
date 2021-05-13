package com.quwan.tt.asmdemoapp

import android.os.AsyncTask
import android.os.Bundle
import android.os.HandlerThread


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val imageView: ImageView =findViewById(R.id.iv)
//        imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
        Thread()
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                return null
            }

        }.execute()

        HandlerThread("test")
    }



}