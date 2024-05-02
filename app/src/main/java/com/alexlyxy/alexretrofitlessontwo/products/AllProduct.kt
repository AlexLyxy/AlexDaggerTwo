package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllProduct(

    @SerializedName("products")
    @Expose
    var products: List<Product>? = null,

    @SerializedName("total")
    @Expose
    var total: Int? = null,

    @SerializedName("skip")
    @Expose
    var skip: Int? = null,

    @SerializedName("limit")
    @Expose
    var limit: Int? = null
)

//    @SerializedName("products")
//    @Expose
//   val products: List<Product>,
    //val products: List<String> = listOf("One", "Two", "Three")
//    @SerializedName("total")
//    @Expose
//    val total: Int,
//
//    @SerializedName("skip")
//    @Expose
//    val skip: Int,
//
//    @SerializedName("limit")
//    @Expose
//    val limit: Int
//
//)



