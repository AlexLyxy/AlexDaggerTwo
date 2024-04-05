package com.alexlyxy.alexretrofitlessontwo.networking

import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.products.ProductDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi  {

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Response<ProductDetails>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>

}
