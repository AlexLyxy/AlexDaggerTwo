package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app.AppComponent
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    val activity: AppCompatActivity,
    private val appComponent: AppComponent
) {

//    private val screensNavigator by lazy {
//        ScreensNavigator(activity)
//    }

    @Provides
    fun activity() = activity

    @Provides
    fun application() = appComponent.application()

    @Provides
    @ActivityScope
    fun screensNavigator(activity: AppCompatActivity) = ScreensNavigator(activity)

    @Provides
    fun layoutInflater() = LayoutInflater.from(activity)

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun productApi() = appComponent.productApi()

}