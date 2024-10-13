package com.alexlyxy.alexretrofitlessontwo

import android.app.Application
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.AppCompositionRoot

class MyApplication: Application() {

    public lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot(this)
        super.onCreate()
    }
}