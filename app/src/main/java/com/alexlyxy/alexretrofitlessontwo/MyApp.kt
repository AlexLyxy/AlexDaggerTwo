package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application(){

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
