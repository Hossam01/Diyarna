package com.example.diyarna.domain.repositry

import com.example.diyarna.data.remote.model.Campaigns
import com.example.diyarna.data.remote.model.ProductModel
import retrofit2.Response

interface DataRepo {

    fun save(key:String,value:String)

    suspend fun get(key:String):String?

    suspend fun getProduct():Response<ProductModel>

    suspend fun getCampaigns():Response<Campaigns>
}