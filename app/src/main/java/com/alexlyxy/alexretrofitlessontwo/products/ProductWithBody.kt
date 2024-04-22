package com.alexlyxy.alexretrofitlessontwo.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductWithBody(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String
)
