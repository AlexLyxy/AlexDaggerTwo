package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation

import com.alexlyxy.alexdaggertwo.screens.product.ProductActivity
import com.alexlyxy.alexdaggertwo.screens.product.ProductFragment
import com.alexlyxy.alexdaggertwo.screens.productdetails.DetailsActivity
import com.alexlyxy.alexdaggertwo.screens.viewmodel.ViewModelActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class, ViewModelModule::class])
interface PresentationComponent {
    fun inject(fragment: ProductFragment)
    fun inject(activity: DetailsActivity)
    fun inject(productActivity: ProductActivity)
    fun inject(viewModelActivity: ViewModelActivity)
}