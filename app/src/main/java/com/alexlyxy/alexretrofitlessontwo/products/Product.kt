//package com.alexlyxy.alexretrofitlessontwo.products
//
//data class Product (
//    val id: Int,
//    var title: String,
//    var description: String
////    val images: List<String>
//)

package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


data class Product(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String

//    @SerializedName("price")
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


