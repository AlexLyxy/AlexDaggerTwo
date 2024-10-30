package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.ActivityScope
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreensNavigator  (private val activity: AppCompatActivity){

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toProductDetails(productId: Int) {
        DetailsActivity.start(activity, productId)
    }
}