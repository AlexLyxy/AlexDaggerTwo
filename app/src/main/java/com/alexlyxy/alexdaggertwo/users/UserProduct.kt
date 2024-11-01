package com.alexlyxy.alexdaggertwo.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserProduct(

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("images")
    @Expose
    var images: List<String>
)
