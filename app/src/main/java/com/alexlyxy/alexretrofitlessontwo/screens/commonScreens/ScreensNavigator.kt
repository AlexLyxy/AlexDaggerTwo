package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens

import android.app.Activity
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import javax.inject.Inject

class ScreensNavigator @Inject constructor (private val activity: Activity){

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toProductDetails(productId: Int) {
        DetailsActivity.start(activity, productId)
    }
}