package com.example.diyarna.domain.di

import android.app.Application
import android.content.Context
import com.example.diyarna.BuildConfig
import com.example.diyarna.data.local.SharedPreferencesManager
import com.example.diyarna.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    const val CONNECTION_TIME_OUT_TIME = 60L

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(CONNECTION_TIME_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIME_OUT_TIME, TimeUnit.SECONDS)
                .callTimeout(CONNECTION_TIME_OUT_TIME, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIME_OUT_TIME, TimeUnit.SECONDS)
                .build()
        } else {
            return OkHttpClient
                .Builder()
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

//    @Provides
//    @Singleton
//    fun provideStockDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, ROOM_DATA_BASE_NAME)
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
////    @Provides
////    fun provideAppDao(appDatabase: AppDatabase) =
////        appDatabase.dao()

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(@ApplicationContext appContext: Context): SharedPreferencesManager {
        return SharedPreferencesManager(appContext.applicationContext)
    }


}