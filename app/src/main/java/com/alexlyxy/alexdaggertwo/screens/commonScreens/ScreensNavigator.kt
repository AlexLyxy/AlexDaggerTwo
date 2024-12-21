package com.alexlyxy.alexdaggertwo.screens.commonScreens

interface ScreensNavigator {

    fun navigateBack()

    fun toProductDetails(productId: Int)
    fun toViewModel()
}
