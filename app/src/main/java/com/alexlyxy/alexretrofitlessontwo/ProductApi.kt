package com.alexlyxy.alexretrofitlessontwo

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi{
//    @GET("products/{id}")
//    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products")
    suspend fun getAllProductById(@Path("products")product: String): List<Product>
}