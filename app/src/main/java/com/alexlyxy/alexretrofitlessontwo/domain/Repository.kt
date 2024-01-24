package com.alexlyxy.alexretrofitlessontwo.domain

interface Repository {
    suspend fun getProductById(id: Int): Product
}
