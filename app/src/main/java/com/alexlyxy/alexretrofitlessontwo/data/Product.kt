package com.alexlyxy.alexretrofitlessontwo.data

data class Product(
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