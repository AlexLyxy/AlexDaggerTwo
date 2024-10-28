package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation

import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule  {

    @Provides
   fun fetchProductUseCase (productApi: ProductApi) = FetchProductUseCase(productApi)

    @Provides
   fun fetchProductDetailsUseCase (productApi: ProductApi) = FetchProductDetailsUseCase(productApi)
}