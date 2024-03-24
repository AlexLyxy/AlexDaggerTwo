package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import retrofit2.Response

interface ProductAppRepository {

    suspend fun getProductApp(id: Int): Response<Product>

}