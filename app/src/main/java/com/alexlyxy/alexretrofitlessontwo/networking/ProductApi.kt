package com.alexlyxy.alexretrofitlessontwo.networking

import com.alexlyxy.alexretrofitlessontwo.products.AllProduct
import com.alexlyxy.alexretrofitlessontwo.products.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi  {

    @GET("products/{products}")
    suspend fun getAllProduct(@Path("products") products: String): Response<AllProduct>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>

}
