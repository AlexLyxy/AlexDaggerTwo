package com.alexlyxy.alexdaggertwo.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexlyxy.alexdaggertwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexdaggertwo.products.FetchProductUseCase
import com.alexlyxy.alexdaggertwo.products.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val fetchProductUseCase: FetchProductUseCase,
    private val fetchProductDetailsUseCase: FetchProductDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _products: MutableLiveData<List<Product>>
    = savedStateHandle.getLiveData("products")
    val products: LiveData<List<Product>> get() = _products

    init {
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
