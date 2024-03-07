package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface Repository {
    suspend fun getLatestProduct(id: Int ): Product

    suspend fun getLocalProduct (id: Int): Product
}