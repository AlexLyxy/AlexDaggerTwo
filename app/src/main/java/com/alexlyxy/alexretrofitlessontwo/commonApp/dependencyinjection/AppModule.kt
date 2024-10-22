package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(val application: Application) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val stackoverflowApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    @Provides
    fun application() = application

    @Provides
    fun productApi() = stackoverflowApi

}