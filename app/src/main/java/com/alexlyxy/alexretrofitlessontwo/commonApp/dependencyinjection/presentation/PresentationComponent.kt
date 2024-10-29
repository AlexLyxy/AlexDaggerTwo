package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation

import com.alexlyxy.alexretrofitlessontwo.screens.product.ProductActivity
import com.alexlyxy.alexretrofitlessontwo.screens.product.ProductFragment
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent()
interface PresentationComponent {
    fun inject(fragment: ProductFragment)
    fun inject(activity: DetailsActivity)
    fun inject(productActivity: ProductActivity)
}