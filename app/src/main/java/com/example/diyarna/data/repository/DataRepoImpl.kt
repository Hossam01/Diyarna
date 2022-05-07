package com.example.diyarna.data.repository

import com.example.diyarna.data.local.SharedPreferencesManager
import com.example.diyarna.domain.repositry.DataRepo
import javax.inject.Inject

class DataRepoImpl @Inject constructor(val preferencesManager: SharedPreferencesManager) : DataRepo {

    override suspend fun save(key: String, value: String) {
        preferencesManager.save(key,value)
    }

    override suspend fun get(key: String): String? {
        return preferencesManager.get(key)
    }



}