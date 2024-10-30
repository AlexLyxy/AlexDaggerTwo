package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.ActivityModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }
    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }
    protected val injector get() = presentationComponent
}