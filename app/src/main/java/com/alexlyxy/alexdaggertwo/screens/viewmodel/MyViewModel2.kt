package com.alexlyxy.alexdaggertwo.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexlyxy.alexdaggertwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexdaggertwo.products.FetchProductUseCase
import com.alexlyxy.alexdaggertwo.products.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel2 @Inject constructor(
    private val fetchProductUseCase: FetchProductUseCase,
    private val fetchProductDetailsUseCase: FetchProductDetailsUseCase
): ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

}