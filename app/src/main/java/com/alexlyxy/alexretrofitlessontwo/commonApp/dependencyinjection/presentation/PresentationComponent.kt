package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation

import com.alexlyxy.alexretrofitlessontwo.screens.product.ProductFragment
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent( modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(fragment: ProductFragment)
    fun inject(activity: DetailsActivity)
}