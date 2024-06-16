package com.example.mevi.ui.fragments.data

import com.example.mevi.core.ApiResponceStatus
import com.example.mevi.core.RetrofitConnection
import com.example.mevi.core.makeNetworkCall
import com.mevi.fakestore.ui.fragments.data.CategoriesApieClient
import com.mevi.fakestore.ui.fragments.data.ProductsResponse

class CategoriesRepository {

    val autservice = RetrofitConnection().getRetrofit()

    suspend fun search(
    ): ApiResponceStatus<List<String>> {
        return makeNetworkCall {
            val response = autservice.create(CategoriesApieClient::class.java)
                .categories()
            //evaluateResponce(response.codigo.toString())
            response
        }
    }
    suspend fun products(category: String): ApiResponceStatus<List<ProductsResponse>> {
        return makeNetworkCall {
            val response = autservice.create(CategoriesApieClient::class.java)
                .products(category)
            //evaluateResponce(response.codigo.toString())
            response
        }
    }
}