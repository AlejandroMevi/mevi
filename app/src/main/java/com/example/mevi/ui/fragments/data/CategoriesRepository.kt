package com.example.mevi.ui.fragments.data

import com.example.mevi.core.ApiResponceStatus
import com.example.mevi.core.RetrofitConnection
import com.example.mevi.core.makeNetworkCall
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val autservice: CategoriesApieClient){

    suspend fun search(
    ): ApiResponceStatus<List<String>> {
        return makeNetworkCall {
            val response = autservice.categories()
            //evaluateResponce(response.codigo.toString())
            response
        }
    }
    suspend fun products(category: String): ApiResponceStatus<List<ProductsResponse>> {
        return makeNetworkCall {
            val response = autservice.products(category)
            //evaluateResponce(response.codigo.toString())
            response
        }
    }
}