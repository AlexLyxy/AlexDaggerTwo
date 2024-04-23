package com.alexlyxy.alexretrofitlessontwo.networking

import com.alexlyxy.alexretrofitlessontwo.products.AllProduct
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.google.gson.annotations.SerializedName

class ProductListResponseSchema (@SerializedName("products") val products: List<Product>)