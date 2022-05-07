package com.example.diyarna.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager constructor(val appContext: Context){

    fun save(key: String, value: String) {

        val sharedPreferences: SharedPreferences =
            appContext.getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString(key, value).apply()
    }

    operator fun get(key: String): String? {
        val sharedPreferences: SharedPreferences =
            appContext.getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString(key, null)

        return value
    }

}