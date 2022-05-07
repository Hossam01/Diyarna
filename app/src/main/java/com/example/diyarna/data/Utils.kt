package com.example.diyarna.data

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import androidx.annotation.NonNull
import com.example.diyarna.BuildConfig
import com.google.android.material.snackbar.Snackbar

import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.muddzdev.styleabletoast.StyleableToast
import java.util.*


object Utils {
    fun logError(tag: String?, msg: String) {
        if (BuildConfig.DEBUG) Log.d(tag, msg)
    }

    fun logInfo(tag: String?, msg: String) {
        if (BuildConfig.DEBUG) Log.i(tag, msg)
    }

    fun LogError(TAG: String, msg: String) {
        if (!BuildConfig.DEBUG) Log.e(TAG, msg)
    }

    fun LogInfo(TAG: String, msg: String) {
        if (!BuildConfig.DEBUG) Log.i(TAG, msg)
    }

    fun LogDebug(TAG: String, msg: String) {
        if (!BuildConfig.DEBUG) Log.d(TAG, msg)
    }

    fun LogWarning(TAG: String, msg: String) {
        if (!BuildConfig.DEBUG) Log.w(TAG, msg)
    }

    fun isNetworkConnected(mContext: Context): Boolean {
        val connMgr = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeInfo = connMgr.activeNetworkInfo
        return if (activeInfo != null && activeInfo.isConnected) {
            true
        } else {
            false
        }
    }



    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager = view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard(view: View) {
        val imm: InputMethodManager = view.getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.getWindowToken(),
            0 /*InputMethodManager.HIDE_IMPLICIT_ONLY*/
        )
    }

    fun showToast(mContext: Context, msg: String?) {
        StyleableToast.Builder(mContext)
            .backgroundColor(Color.WHITE)
            .textColor(Color.BLACK)
            .solidBackground()
            .text(msg)
            .show()
    }


}