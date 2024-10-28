package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.ActivityModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.DaggerActivityComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy {
        DaggerActivityComponent.builder()
            .appComponent(appComponent)
            .activityModule(ActivityModule(this))
            .build()
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }
    protected val injector get() = presentationComponent
}