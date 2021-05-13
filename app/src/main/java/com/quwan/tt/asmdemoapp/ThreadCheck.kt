package com.quwan.tt.asmdemoapp

import android.util.Log

object ThreadCheck {

    var isCanAppendLog = false
    private const val tag = "====>ThreadCheck"

    fun printThread(name : String){

        println("====>printThread:${name}")

        val es = Thread.currentThread().stackTrace

        val normalInfo = StringBuilder(" \nThreadTrace:")
            .append("\nthreadName:${name}")
            .append("\n====================================threadTraceStart=======================================")

        for (e in es) {

            if (e.className == "dalvik.system.VMStack" && e.methodName == "getThreadStackTrace") {
                isCanAppendLog = false
            }

            if (e.className.contains("ThreadCheck") && e.methodName == "printThread") {
                isCanAppendLog = true
            } else {
                if (isCanAppendLog) {
                    normalInfo.append("\n${e.className}(lineNumber:${e.lineNumber})")
                }
            }
        }
        normalInfo.append("\n=====================================threadTraceEnd=======================================")

        Log.i(tag, normalInfo.toString())
    }

}