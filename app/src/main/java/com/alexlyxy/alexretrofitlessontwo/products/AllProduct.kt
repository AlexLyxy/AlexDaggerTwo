package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllProduct(
    @SerializedName("products")
    @Expose
    val products: List<Product>,

    @SerializedName("total")
    @Expose
    val total: Int? = null,

    @SerializedName("skip")
    @Expose
    val skip: Int? = null,

    @SerializedName("limit")
    @Expose
    val limit: Int? = null,

    val images: List<String>
)
