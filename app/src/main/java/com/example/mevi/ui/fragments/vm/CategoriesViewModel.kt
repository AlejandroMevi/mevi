package com.example.mevi.ui.fragments.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mevi.core.ApiResponceStatus
import com.example.mevi.ui.fragments.data.CategoriesRepository
import com.example.mevi.ui.fragments.data.ProductsResponse
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val repository = CategoriesRepository()

    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set

    var data = MutableLiveData<List<String>?>(null)
        private set
    var statusProducts = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set

    var dataProducts = MutableLiveData<List<ProductsResponse>?>(null)
        private set

    fun getListCategories() {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = repository.search()
            if (responce is ApiResponceStatus.Success) {
                data.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }

    fun getProducts(category: String) {
        viewModelScope.launch {
            statusProducts.value = ApiResponceStatus.Loading()
            val responce = repository.products(category)
            if (responce is ApiResponceStatus.Success) {
                dataProducts.value = responce.data
            }
            statusProducts.value = responce as ApiResponceStatus<Any>
        }
    }
}