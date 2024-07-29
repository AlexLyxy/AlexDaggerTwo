package com.alexlyxy.alexretrofitlessontwo.commonApp.composition

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ViewMvcFactory

class ActivityCompositionRoot(
//    private val activity: AppCompatActivity,
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