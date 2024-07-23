package com.alexlyxy.alexretrofitlessontwo.commonApp.composition

import android.app.Activity
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ScreensNavigator

class ActivityCompositionRoot(
    private val activity: Activity,
    private val appCompositionRoot: AppCompositionRoot
){

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

   private val productApi get() = appCompositionRoot.productApi

    val fetchProductUseCase get() = FetchProductUseCase(productApi)

    val fetchProductDetailsUseCase get() = FetchProductDetailsUseCase(productApi)

//    val fetchProductUseCase get() = appCompositionRoot.fetchProductUseCase
//
//    val fetchProductDetailsUseCase get() = appCompositionRoot.fetchProductDetailsUseCase

}