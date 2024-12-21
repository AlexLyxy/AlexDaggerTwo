package com.alexlyxy.alexdaggertwo.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alexlyxy.alexdaggertwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexdaggertwo.products.FetchProductUseCase
import com.alexlyxy.alexdaggertwo.products.Product
import com.alexlyxy.alexdaggertwo.screens.commonScreens.viewmodels.SavedStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel  @Inject constructor(
    private val fetchProductUseCase:  FetchProductUseCase,
    private val fetchProductDetailsUseCase: FetchProductDetailsUseCase
): SavedStateViewModel(){

    private lateinit var _products: MutableLiveData<List<Product>>
    val products: LiveData<List<Product>> get() = _products

    override fun init(savedStateHandle: SavedStateHandle) {
        _products = savedStateHandle.getLiveData("products")

        viewModelScope.launch {
            delay(5000)
            val result = fetchProductUseCase.fetchLatestProduct()
            if (result is FetchProductUseCase.Result.Success) {
                _products.value = result.products
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }
}