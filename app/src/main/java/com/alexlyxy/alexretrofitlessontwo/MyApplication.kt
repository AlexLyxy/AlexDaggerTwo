package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app.AppModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app.DaggerAppComponent

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