package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.data.ProductApiApp
import com.alexlyxy.alexretrofitlessontwo.data.ProductRepositoryImpl
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application (){

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    public val productApiApp: ProductApiApp = retrofit.create(ProductApiApp::class.java)

    public val getProductAppUseCase get() = GetProductUseCase(productRepository = ProductRepositoryImpl())

    override fun onCreate() {
        super.onCreate()
    }
}