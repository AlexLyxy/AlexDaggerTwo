package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    public val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    public val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    public val fetchProductUseCase get() = FetchProductUseCase(productApi)

    public val fetchProductDetailsUseCase get() = FetchProductDetailsUseCase(productApi)

    override fun onCreate() {
        super.onCreate()
    }
}