package com.example.diyarna.data.remote

import com.example.diyarna.data.remote.model.Campaigns
import com.example.diyarna.data.remote.model.CampaignsItem
import com.example.diyarna.data.remote.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Hossam01/Hossam01/main/product.json")
    suspend fun getProducts(): Response<ProductModel>

    @GET("Hossam01/Hossam01/main/campaignsItems.json")
    suspend fun getCampaigns(): Response<Campaigns>
}