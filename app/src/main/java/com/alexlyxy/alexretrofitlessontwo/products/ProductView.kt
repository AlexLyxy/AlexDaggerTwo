package com.alexlyxy.alexretrofitlessontwo.products

data class ProductView (
    val id: Int,
    var title: String,
    val images: List<String>
)