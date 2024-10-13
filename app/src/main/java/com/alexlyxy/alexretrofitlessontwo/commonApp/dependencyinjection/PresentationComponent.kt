package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection

import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ViewMvcFactory
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun viewMvcFactory(): ViewMvcFactory

    fun screensNavigator(): ScreensNavigator

    fun dialogsNavigator(): DialogsNavigator

    fun fetchProductUseCase(): FetchProductUseCase

    fun fetchProductDetailsUseCase(): FetchProductDetailsUseCase
}