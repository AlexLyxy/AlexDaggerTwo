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

    @Provides
    @AppScope
    fun retrofit() : Retrofit {
         return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun application() = application

    @AppScope
    @Provides
    fun productApi(retrofit: Retrofit) = retrofit.create(ProductApi::class.java)

}