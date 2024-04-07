package com.alexlyxy.alexretrofitlessontwo

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi{
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products/{products}")
    suspend fun getAllProduct(@Path("products")products: String): AllProduct
}