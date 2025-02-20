package com.sm.android.baseproject.utils

import android.util.Log

object LogsUtils {

    const val TAG = "MyTag"

    fun Any?.printToDebugLog(tag: String = TAG) {
        Log.d(tag, toString())
    }

    fun Exception.printToErrorLog(message: Any, tag: String = TAG) {
        Log.e(tag, message.toString(), this)
    }
}