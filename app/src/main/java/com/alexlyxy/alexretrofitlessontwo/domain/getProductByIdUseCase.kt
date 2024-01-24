package com.alexlyxy.alexretrofitlessontwo.domain

import android.util.Log

class getProductByIdUseCase (private val repository: Repository) {
    suspend fun getProductById(id: Int) {
        repository.getProductById(id)
       // Log.d("MyLog", "Product: $product")
   }
}
