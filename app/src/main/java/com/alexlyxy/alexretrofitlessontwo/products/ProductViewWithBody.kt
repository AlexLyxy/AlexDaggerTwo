package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductViewWithBody (
    val id: Int,
    var title: String,
    val images: List<String>,
    @SerializedName("products")
    @Expose
    val products: List<Product>? = null

)