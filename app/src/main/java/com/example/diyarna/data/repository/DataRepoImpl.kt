package com.example.diyarna.data.repository

import android.util.Log
import android.widget.Toast
import com.example.diyarna.data.local.SharedPreferencesManager
import com.example.diyarna.data.remote.ApiService
import com.example.diyarna.data.remote.model.Campaigns
import com.example.diyarna.data.remote.model.CampaignsItem
import com.example.diyarna.data.remote.model.ProductModel
import com.example.diyarna.domain.repositry.DataRepo
import retrofit2.Response
import javax.inject.Inject

class DataRepoImpl @Inject constructor(val preferencesManager: SharedPreferencesManager,val apiService: ApiService) : DataRepo {

    override fun save(key: String, value: String) {
        preferencesManager.save(key,value)
    }

    override suspend fun get(key: String): String? {
        return preferencesManager.get(key)
    }

    override fun remove(key: String):Boolean {
       return preferencesManager.remove(key)
    }


    override suspend fun getProduct(): Response<ProductModel> {

        try {
            return apiService.getProducts()
        } catch (e: Exception) {
            Log.d("TAG", "getGuides: "+e.message)
            return Response.success(null)
        }

    }

    override suspend fun getCampaigns(): Response<Campaigns> {
        try {
            return apiService.getCampaigns()
        } catch (e: Exception) {
            Log.d("TAG", "getGuides: "+e.message)
            return Response.success(null)
        }

    }


}