package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.AppModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.DaggerAppComponent

class MyApplication: Application() {

    public val appComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}