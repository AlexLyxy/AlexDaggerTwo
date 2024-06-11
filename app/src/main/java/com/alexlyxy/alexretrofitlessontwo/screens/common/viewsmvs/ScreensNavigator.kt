package com.alexlyxy.alexretrofitlessontwo.screens.common.viewsmvs

import android.app.Activity
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity

class ScreensNavigator  (private val activity: Activity){

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetails(productId: Int) {
        DetailsActivity.start(activity, productId)
    }
}