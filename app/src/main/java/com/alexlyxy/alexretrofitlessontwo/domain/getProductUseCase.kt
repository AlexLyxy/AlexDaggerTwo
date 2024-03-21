package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import com.alexlyxy.alexretrofitlessontwo.data.ProductRepositoryImpl
import retrofit2.Response

class GetProductUseCase(private val productRepository: ProductRepositoryImpl) {

    suspend fun getLatestProduct(): Response<Product> {
        return productRepository.getLatestProduct(0)
    }

    suspend fun getLocalProduct(): Response<Product> {
        return productRepository.getLocalProduct(id = 0)
    }
}
