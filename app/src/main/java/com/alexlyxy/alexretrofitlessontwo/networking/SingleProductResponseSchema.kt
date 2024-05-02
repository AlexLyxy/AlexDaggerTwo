package com.alexlyxy.alexretrofitlessontwo.networking

import com.alexlyxy.alexretrofitlessontwo.products.ProductWithBody
import com.google.gson.annotations.SerializedName

data class SingleProductResponseSchema(@SerializedName("products") val products: List<ProductWithBody>){
    val product: ProductWithBody get() = products[0]
}
