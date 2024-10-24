package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ViewMvcFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule  {

//    @Provides
//    fun layoutInflater () = activityComponent.layoutInflater()
//
//    @Provides
//   fun fragmentManager () = activityComponent.fragmentManager()
//
//    @Provides
//   fun productApi () = activityComponent.productApi()
//
//    @Provides
//   fun activity () = activityComponent.activity()
//
//    @Provides
//    fun screensNavigator () = activityComponent.screensNavigator()

    @Provides
    fun viewMvcFactory (layoutInflater: LayoutInflater) = ViewMvcFactory(layoutInflater)

    @Provides
   fun dialogsNavigator (fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

    @Provides
   fun fetchProductUseCase (productApi: ProductApi) = FetchProductUseCase(productApi)

    @Provides
   fun fetchProductDetailsUseCase (productApi: ProductApi) = FetchProductDetailsUseCase(productApi)
}