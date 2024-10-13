package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.ActivityCompositionRoot
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.Injector
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.PresentationModule
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.ProductDetailsViewMvc

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    //    protected val compositionRoot get() = PresentationCompositionRoot(activityCompositionRoot)
//    protected val compositionRoot by lazy {
//        PresentationModule(activityCompositionRoot)
//    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityCompositionRoot))
            .build()
    }

    private lateinit var viewMvc: ProductDetailsViewMvc
    protected val injector get() = Injector(presentationComponent)
}