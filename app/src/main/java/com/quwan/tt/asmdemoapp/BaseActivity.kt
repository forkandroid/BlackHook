package com.quwan.tt.asmdemoapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewDelegate()
        super.onCreate(savedInstanceState)
    }

    open fun setViewDelegate() {
        getFactory2()?.let {
            LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), it)
        }
    }

    open fun getFactory2() : LayoutInflater.Factory2?{
        return null
    }
}