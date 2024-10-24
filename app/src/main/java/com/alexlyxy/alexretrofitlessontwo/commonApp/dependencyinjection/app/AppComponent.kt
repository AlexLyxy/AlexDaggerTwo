package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationScope
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import dagger.Component

@PresentationScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application
    fun productApi(): ProductApi

}