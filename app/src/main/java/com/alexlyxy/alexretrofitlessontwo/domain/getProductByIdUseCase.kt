package com.alexlyxy.alexretrofitlessontwo.domain

import android.util.Log

class GetProductByIdUseCase (private val repository: Repository) {
   // suspend fun getProductById(id: Int) : Product{
    suspend fun execute() : Product{
        return  repository.getProductById(id=1)
       // Log.d("MyLog", "Product: $product")
   }
}
