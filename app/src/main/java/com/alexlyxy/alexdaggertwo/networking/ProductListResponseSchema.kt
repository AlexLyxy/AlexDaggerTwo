package com.alexlyxy.alexdaggertwo.networking

import com.alexlyxy.alexdaggertwo.products.Product
import com.google.gson.annotations.SerializedName

class ProductListResponseSchema (
    @SerializedName("products") val products: List<Product>)