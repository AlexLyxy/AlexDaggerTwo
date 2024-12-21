package com.alexlyxy.alexdaggertwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.MyApplication
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponentBuilder()
            .activity(this)
            .build()
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(
            PresentationModule(this))
    }

    protected val injector get() = presentationComponent
}