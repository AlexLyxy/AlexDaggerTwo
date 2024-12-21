package com.alexlyxy.alexdaggertwo.screens.commonScreens

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.screens.productdetails.DetailsActivity
import com.alexlyxy.alexdaggertwo.screens.viewmodel.ViewModelActivity
import javax.inject.Inject

class ScreensNavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity): ScreensNavigator {

    override fun navigateBack() {
        activity.onBackPressed()
    }

    override fun toProductDetails(productId: Int) {
        DetailsActivity.start(activity, productId )
    }

    override fun toViewModel() {
        ViewModelActivity.start(activity)
    }
}