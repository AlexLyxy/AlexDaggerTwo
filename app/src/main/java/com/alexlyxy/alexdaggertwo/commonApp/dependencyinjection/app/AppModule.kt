package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.app

import android.app.Application
import com.alexlyxy.alexdaggertwo.Constants
import com.alexlyxy.alexdaggertwo.networking.ProductApi
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

    @Provides
    @AppScope
    fun productApi(retrofit: Retrofit) = retrofit.create(ProductApi::class.java)

}