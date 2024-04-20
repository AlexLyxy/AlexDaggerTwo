package com.alexlyxy.alexretrofitlessontwo.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi  {
    @GET("products/{products}")
    suspend fun getAllProduct(@Path("products") products: String): Response<ProductListResponseSchema>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<SingleProductResponseSchema>
}
