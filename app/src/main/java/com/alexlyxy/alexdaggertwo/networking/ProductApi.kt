package com.alexlyxy.alexdaggertwo.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products/{products}")
    //suspend fun getAllProduct(@Path("products") products: String): Response<AllProduct>
    suspend fun getAllProduct(@Path("products") products: String): Response<ProductListResponseSchema>
}
