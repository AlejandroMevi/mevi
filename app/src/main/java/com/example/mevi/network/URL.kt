package com.example.mevi.network

import com.example.mevi.core.ApiInterceptor
import com.example.mevi.core.Constants.Companion.TIME_OUT
import com.example.mevi.ui.fragments.data.CategoriesApieClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class URL {
    companion object {
        var URL_BASE = "https://fakestoreapi.com/products/"
        var URL_SECOND = "https://eland-dk.humaneland.net/HumaneTime/api/"
    }
    class Path {
        companion object {
            const val CATEGORIES = "categories"
            const val PRODUCTS = "category"
        }
    }

    private val okHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .pingInterval(3, TimeUnit.SECONDS)
        .addInterceptor(ApiInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun newUrl(url: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCategorieApiClient(retrofit: Retrofit): CategoriesApieClient {
        return retrofit.create(CategoriesApieClient::class.java)
    }
    /*
    @Provides
    @Singleton
    fun provideMainTimeZoneApiService(): FreeStationApiClient {
        val retrofit = newUrl(URL_FREESTATION)
        return retrofit.create(FreeStationApiClient::class.java)
    }
     */
}