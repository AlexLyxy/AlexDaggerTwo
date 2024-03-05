package com.alexlyxy.alexretrofitlessontwo.data

import com.google.gson.annotations.SerializedName

data class Product(
   // (@SerializedName("products/{id}")
    val id: Int,
    var title: String,
   var description: String,
    val price: String,
    val discountPercentage: String,
    val rating: String,
    val stock: String,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
