package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.app

import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.Retrofit1
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.Retrofit2
import com.alexlyxy.alexdaggertwo.networking.ProductApi
import com.alexlyxy.alexdaggertwo.networking.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @AppScope
    @Retrofit1
    fun retrofit1(urlProvider: UrlProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl1())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    @Retrofit2
    fun retrofit2(urlProvider: UrlProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl2())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AppScope
    @Provides
    fun urlProvider() = UrlProvider()


    @Provides
    @AppScope
    fun productApi(@Retrofit1 retrofit: Retrofit) = retrofit.create(ProductApi::class.java)

}