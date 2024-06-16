package com.example.mevi.ui.fragments.data

data class ProductsResponse(
    val id: Int? = null,
    val title: String? = null,
    val price: String? = null,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val rating: Rating? = null
)

data class Rating(
    val rate: Double,
    val count: Int
)