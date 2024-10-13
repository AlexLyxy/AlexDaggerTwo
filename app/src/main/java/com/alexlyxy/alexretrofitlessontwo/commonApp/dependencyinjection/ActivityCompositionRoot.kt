package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator

class ActivityCompositionRoot(
    val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
){

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val application get() = appCompositionRoot.application

    val layoutInflater get() = LayoutInflater.from(activity)

    val fragmentManager get() = activity.supportFragmentManager

    val productApi get() = appCompositionRoot.productApi

}