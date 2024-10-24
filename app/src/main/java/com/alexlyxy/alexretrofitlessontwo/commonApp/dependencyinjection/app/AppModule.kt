package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationScope
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(val application: Application) {

//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @Provides
    @PresentationScope
    fun retrofit() : Retrofit {
         return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun application() = application

    @PresentationScope
    @Provides
    fun productApi(retrofit: Retrofit) = retrofit.create(ProductApi::class.java)

}