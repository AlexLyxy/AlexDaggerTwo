package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product

class GetProductUseCase (private  val repository: Repository) {

    suspend fun getLatestProduct(): Product {
        return  repository.getLatestProduct(0)
    }

    suspend fun getLocalProduct(): Product {
        return  repository.getLocalProduct(
            id = 0
        )
    }
}




