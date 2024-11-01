package com.alexlyxy.alexdaggertwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.MyApplication
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity.ActivityModule

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