package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.composition.ActivityCompositionRoot
import com.alexlyxy.alexretrofitlessontwo.commonApp.composition.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    //    protected val compositionRoot get() = PresentationCompositionRoot(activityCompositionRoot)
    protected val compositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }
}