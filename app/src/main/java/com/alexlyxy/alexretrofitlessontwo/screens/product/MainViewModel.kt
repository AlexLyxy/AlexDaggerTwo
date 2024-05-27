package com.alexlyxy.alexretrofitlessontwo.screens.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexlyxy.alexretrofitlessontwo.products.ProductModel

class MainViewModel: ViewModel() {
    val liveDataList = MutableLiveData<List<ProductModel>>()

}