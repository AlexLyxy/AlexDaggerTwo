package com.alexlyxy.alexretrofitlessontwo.commonApp.composition

import android.app.Application
import android.support.annotation.UiThread
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@UiThread
class AppCompositionRoot(val application: Application) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val fetchProductUseCase get() = FetchProductUseCase(productApi)

    val fetchProductDetailsUseCase get() = FetchProductDetailsUseCase(productApi)

}