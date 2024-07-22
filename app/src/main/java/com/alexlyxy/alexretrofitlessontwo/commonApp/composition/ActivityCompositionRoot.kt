package com.alexlyxy.alexretrofitlessontwo.commonApp.composition

import android.app.Activity
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ScreensNavigator

class ActivityCompositionRoot (private val activity: Activity){

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }
}