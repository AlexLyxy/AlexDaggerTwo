package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application (){

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    public val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    public val getProductUseCase get() = GetProductUseCase(productApi)

    override fun onCreate() {
        super.onCreate()
    }
}