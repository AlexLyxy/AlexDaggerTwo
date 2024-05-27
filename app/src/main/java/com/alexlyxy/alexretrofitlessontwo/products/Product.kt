package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Product(
    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("category")
    @Expose
    val category: String?,

    @SerializedName("price")
    @Expose
    val price: Double?,

    @SerializedName("discountPercentage")
    @Expose
    val discountPercentage: Double?,

    @SerializedName("rating")
    @Expose
    val rating: Double?,

    @SerializedName("stock")
    @Expose
    val stock: Int?,

    @SerializedName("tags")
    @Expose
    val tags: List<String>?,

    @SerializedName("brand")
    @Expose
    val brand: String?,

    @SerializedName("images")
    @Expose
    val images: List<String>,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String?
)



