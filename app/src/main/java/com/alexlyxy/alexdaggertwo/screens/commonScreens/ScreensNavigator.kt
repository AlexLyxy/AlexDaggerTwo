package com.alexlyxy.alexdaggertwo.screens.commonScreens

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity.ActivityScope
import com.alexlyxy.alexdaggertwo.screens.productdetails.DetailsActivity

@ActivityScope
class ScreensNavigator  (private val activity: AppCompatActivity){

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun toProductDetails(productId: Int) {
        DetailsActivity.start(activity, productId)
    }
}