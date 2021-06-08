package com.quwan.tt.asmdemoapp

import android.util.Log

class ServiceHook {
    private val tag = "====>ServiceHook"

    fun printStartServiceStackTrace(){
        val es = Thread.currentThread().stackTrace

        val normalInfo = StringBuilder(" \nServiceTrace:")
            .append("\n====================================ServiceTraceStart=======================================")

        println("====>es:${es.size}")

        for (e in es) {
            normalInfo.append("\n${e.className}(lineNumber:${e.lineNumber})")
        }
        normalInfo.append("\n=====================================ServiceTraceEnd=======================================")
        Log.i(tag, normalInfo.toString())
    }

}