package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.ActivityCompositionRoot
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.*

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    private val presentationComponent  by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityCompositionRoot))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}