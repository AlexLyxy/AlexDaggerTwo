package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product

class GetProductByIdUseCase (private val repository: Repository) {
     suspend fun  getProduct(): Product {
        return  repository.getProductById(id=1)
   }
}

//Log.d("MyLog", "Product: $product")