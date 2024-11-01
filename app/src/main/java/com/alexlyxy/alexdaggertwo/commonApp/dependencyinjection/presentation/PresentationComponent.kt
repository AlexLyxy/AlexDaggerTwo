package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation

import com.alexlyxy.alexdaggertwo.screens.product.ProductActivity
import com.alexlyxy.alexdaggertwo.screens.product.ProductFragment
import com.alexlyxy.alexdaggertwo.screens.productdetails.DetailsActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent()
interface PresentationComponent {
    fun inject(fragment: ProductFragment)
    fun inject(activity: DetailsActivity)
    fun inject(productActivity: ProductActivity)
}