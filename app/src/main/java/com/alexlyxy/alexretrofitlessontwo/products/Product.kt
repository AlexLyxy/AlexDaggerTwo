package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Product(
    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

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

//    @SerializedName("tags")
//    @Expose
//    val tags: List<String>?,

    @SerializedName("brand")
    @Expose
    val brand: String?,

    @SerializedName("images")
    @Expose
    var images: List<String>,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String?
)

//
//data class Product(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val category: String,
//    val price: Double,
//    val discountPercentage: Double,
//    val rating: Double,
//    val stock: Int,
//    val tags: List<String>,
//    val brand: String,
//    val sku: String,
//    val weight: Int,
//    val dimensions: Dimensions,
//    val warrantyInformation: String,
//    val shippingInformation: String,
//    val availabilityStatus: String,
//    val reviews: List<Review>,
//    val returnPolicy: String,
//    val minimumOrderQuantity: Int,
//    val meta: Meta,
//    val thumbnail: String,
//    val images: List<String>
//)


