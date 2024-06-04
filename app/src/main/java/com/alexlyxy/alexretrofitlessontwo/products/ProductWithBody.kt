package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductWithBody(

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

    @SerializedName("brand")
    @Expose
    val brand: String?,

    @SerializedName("images")
    @Expose
    var images: List<String>,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String?


//    @SerializedName("products")
//    @Expose
//    val products: List<Product>,

//    @SerializedName("id")
//    @Expose
//    var id: Int,
//
//    @SerializedName("title")
//    @Expose
//    var title: String,
//
//    @SerializedName("description")
//    @Expose
//    var description: String,
//
//        @SerializedName("price")
//    @Expose
//    var price: Int,
//
//    @SerializedName("discountPercentage")
//    @Expose
//    var discountPercentage: Double,
//
//    @SerializedName("rating")
//    @Expose
//    var rating: Double,
//
//    @SerializedName("stock")
//    @Expose
//    var stock: Int,
//
//    @SerializedName("brand")
//    @Expose
//    var brand: String,
//
//    @SerializedName("category")
//    @Expose
//    var category: String,
//
//    @SerializedName("thumbnail")
//    @Expose
//    var thumbnail: String,
//
//    @SerializedName("images")
//    @Expose
//    var images: List<String>
)
