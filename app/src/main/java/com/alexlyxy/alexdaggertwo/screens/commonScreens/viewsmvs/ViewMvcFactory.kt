package com.alexlyxy.alexdaggertwo.screens.commonScreens.viewsmvs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexlyxy.alexdaggertwo.screens.commonScreens.imageloader.ImageLoader
import com.alexlyxy.alexdaggertwo.screens.product.ProductActivityViewMvc
import com.alexlyxy.alexdaggertwo.screens.productdetails.ProductDetailsViewMvc
import javax.inject.Inject

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) {

    fun newProductViewMvc(parent: ViewGroup?): ProductActivityViewMvc {
        return ProductActivityViewMvc(layoutInflater, parent)
    }

    fun newProductDetailsViewMvc(parent: ViewGroup?): ProductDetailsViewMvc {
        return ProductDetailsViewMvc(layoutInflater,imageLoader, parent)
    }
}