package com.alexlyxy.alexretrofitlessontwo.data

import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.domain.ProductAppRepository
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepositoryAppImpl : ProductAppRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val productApiApp: ProductApiApp = retrofit.create(ProductApiApp::class.java)

    override suspend fun getProductApp(id: Int): Response<Product> {
        return productApiApp.getProduct(4)
    }
}