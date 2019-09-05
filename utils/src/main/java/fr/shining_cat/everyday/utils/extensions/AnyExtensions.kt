package fr.shining_cat.everyday.utils.extensions

import android.util.Log
import fr.shining_cat.everyday.utils.BuildConfig
import fr.shining_cat.everyday.utils.ellipsizeStringFallbackIfNullOrEmpty


//LOGGING
const val ANONYMOUS_CLASS_TAG_LOG = "Anonymous Class"
const val NO_JAVACLASS_NAME_TAG_LOG = "No Class Name"
const val MAX_TAG_LENGTH_IN_LOG = 23

val Any.TAG_LOG: String
    get() {
        val tag = javaClass.simpleName
        return ellipsizeStringFallbackIfNullOrEmpty(
            tag,
            MAX_TAG_LENGTH_IN_LOG,
            NO_JAVACLASS_NAME_TAG_LOG,
            ANONYMOUS_CLASS_TAG_LOG
        )
    }


fun Any.logD(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.d(actualTag, message) else Log.d(actualTag, message, throwable)
    }
}

fun Any.logE(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.e(actualTag, message) else Log.e(actualTag, message, throwable)
    }
}

fun Any.logI(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.i(actualTag, message) else Log.i(actualTag, message, throwable)
    }
}

fun Any.logV(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.v(actualTag, message) else Log.v(actualTag, message, throwable)
    }
}

fun Any.logW(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.w(actualTag, message) else Log.w(actualTag, message, throwable)
    }
}

fun Any.logWTF(message: String, overwriteTag: String? = null, throwable: Throwable? = null){
    if(BuildConfig.DEBUG){
        val actualTag = ellipsizeStringFallbackIfNullOrEmpty(
            overwriteTag,
            MAX_TAG_LENGTH_IN_LOG,
            TAG_LOG,
            TAG_LOG
        )
        if(throwable == null) Log.wtf(actualTag, message) else Log.wtf(actualTag, message, throwable)
    }
}