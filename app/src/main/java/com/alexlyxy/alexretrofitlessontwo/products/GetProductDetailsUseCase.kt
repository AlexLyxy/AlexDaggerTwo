package com.alexlyxy.alexretrofitlessontwo.products

import AllProduct
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetProductDetailsUseCase {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    suspend fun getLatestProductDetails(): Response<AllProduct> {
        return productApi.getAllProduct("")
    }
}