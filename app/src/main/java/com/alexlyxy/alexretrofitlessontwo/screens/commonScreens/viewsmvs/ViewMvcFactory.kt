package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexlyxy.alexretrofitlessontwo.screens.product.ProductActivityViewMvc
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.ProductDetailsViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun newProductViewMvc(parent: ViewGroup?): ProductActivityViewMvc {
        return ProductActivityViewMvc(layoutInflater, parent)
    }

    fun newProductDetailsViewMvc(parent: ViewGroup?): ProductDetailsViewMvc {
        return ProductDetailsViewMvc(layoutInflater, parent)
    }
}