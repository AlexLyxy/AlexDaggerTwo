package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface Repository {

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
    //suspend fun getProductById( ): Product

}
