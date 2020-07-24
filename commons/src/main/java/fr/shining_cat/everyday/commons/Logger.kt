package fr.shining_cat.everyday.commons

import android.util.Log

class Logger {

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(tag, msg)
            } else {
                Log.e(tag, msg, throwable)
            }
        }
    }
}