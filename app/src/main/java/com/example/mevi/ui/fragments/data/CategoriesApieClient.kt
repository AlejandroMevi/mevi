package com.example.mevi.ui.fragments.data

import com.example.mevi.network.URL
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CategoriesApieClient {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Path.CATEGORIES)
    suspend fun categories(): List<String>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("${URL.Path.PRODUCTS}/{category}")
    suspend fun products(
        @Path("category") category: String
    ): List<ProductsResponse>
}