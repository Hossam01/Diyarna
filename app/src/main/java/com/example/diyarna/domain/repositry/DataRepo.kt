package com.example.diyarna.domain.repositry

interface DataRepo {

    suspend fun save(key:String,value:String)

    suspend fun get(key:String):String?
}