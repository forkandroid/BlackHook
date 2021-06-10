package com.quwan.tt.asmdemoapp

import android.app.Notification
import android.app.Service
import android.os.Build

abstract class BaseService: Service() {

    fun startForegroundIfNeed(id: Int, notification: Notification, alwaysStart: Boolean = false) {
        if (alwaysStart || Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(id, notification)
        }
    }

}