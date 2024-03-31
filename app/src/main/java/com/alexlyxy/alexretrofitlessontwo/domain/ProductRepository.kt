package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.Product
import retrofit2.Response

interface ProductRepository {

    suspend fun getLatestProduct(id: Int): Response<Product>

    suspend fun getLocalProduct(id: Int): Response<Product>

}
// suspend fun getLatestProduct(): Response<Product>
