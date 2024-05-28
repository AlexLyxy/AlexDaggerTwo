package com.alexlyxy.alexretrofitlessontwo.products

data class ProductDetailsModel(
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
//    val tags: List<String>?,
    val brand: String,
    var imageOne: String,
    val thumbnail: String
)
