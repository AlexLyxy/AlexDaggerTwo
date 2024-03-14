package com.alexlyxy.alexretrofitlessontwo.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi  {
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>

}
