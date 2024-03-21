package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.data.Product
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetProductUseCase  (private  val productRepository: ProductRepository) {

    suspend fun getLatestProduct(): Product {
        return productRepository.getLatestProduct(0)
    }

    suspend fun getLocalProduct(): Product {
        return productRepository.getLocalProduct(id = 0)
    }
}
