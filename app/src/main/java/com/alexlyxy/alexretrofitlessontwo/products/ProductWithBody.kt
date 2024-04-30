package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductWithBody(

//    @SerializedName("products")
//    @Expose
//    val products: List<Product>,

    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String
)