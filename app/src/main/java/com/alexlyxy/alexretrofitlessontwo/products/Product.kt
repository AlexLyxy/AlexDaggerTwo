package com.alexlyxy.alexretrofitlessontwo.products

data class Product (
    val id: Int,
    var title: String,
    var description: String,
    val images: List<String>
)
