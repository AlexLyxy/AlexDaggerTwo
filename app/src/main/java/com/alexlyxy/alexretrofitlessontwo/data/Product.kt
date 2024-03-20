package com.alexlyxy.alexretrofitlessontwo.data

data class Product(
    val id: Int,
    var title: String,
    var description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
