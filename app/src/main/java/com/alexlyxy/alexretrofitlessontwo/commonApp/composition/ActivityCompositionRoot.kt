package com.alexlyxy.alexretrofitlessontwo.commonApp.composition

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ScreensNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
){

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val fragmentManager get() = activity.supportFragmentManager

    val dialogsNavigator get() = DialogsNavigator(fragmentManager)

   private val productApi get() = appCompositionRoot.productApi

    val fetchProductUseCase get() = FetchProductUseCase(productApi)

    val fetchProductDetailsUseCase get() = FetchProductDetailsUseCase(productApi)

}