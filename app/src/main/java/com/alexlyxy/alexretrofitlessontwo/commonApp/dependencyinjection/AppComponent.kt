package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application
    fun productApi(): ProductApi

}