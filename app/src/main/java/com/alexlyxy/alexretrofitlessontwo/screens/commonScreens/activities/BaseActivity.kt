package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.ActivityModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.DaggerActivityComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.DaggerPresentationComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.Injector
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appComponent

    val activityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this, appCompositionRoot))
            .build()
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityComponent))
            .build()
    }
    protected val injector get() = Injector(presentationComponent)
}