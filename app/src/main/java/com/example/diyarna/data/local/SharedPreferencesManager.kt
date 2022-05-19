package com.example.diyarna.data.local

import android.R.attr
import android.content.Context
import android.content.SharedPreferences
import java.lang.NullPointerException
import android.R.attr.tag
import android.R.attr.tag
import com.example.diyarna.BuildConfig


class SharedPreferencesManager constructor(val appContext: Context){

    private val tag: String = getDefaultSharedPreferencesTag()
    private var sharedPreferences: SharedPreferences? = null


    private fun getDefaultSharedPreferencesTag(): String {
        return BuildConfig.APPLICATION_ID.substring(
            BuildConfig.APPLICATION_ID.lastIndexOf(".") + 1
        )
    }
    @Throws(NullPointerException::class)
    private fun getSharedPreferences(): SharedPreferences {
        if (sharedPreferences == null && appContext != null) sharedPreferences =
            appContext.getSharedPreferences(
                tag, Context.MODE_PRIVATE
            )
        return sharedPreferences!!
    }

    @Throws(NullPointerException::class)
    private fun getEditor(): SharedPreferences.Editor {
        return getSharedPreferences().edit()
    }

    fun save(key: String, value: String) {
         getEditor().putString(key, value).commit()
    }



    operator fun get(key: String): String? {
        return getSharedPreferences().getString(key, null)
    }

    fun remove(key: String): Boolean {
        return getEditor().remove(key).commit()
    }





}