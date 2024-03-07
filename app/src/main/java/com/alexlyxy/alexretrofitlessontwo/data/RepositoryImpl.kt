package com.alexlyxy.alexretrofitlessontwo.data

import retrofit2.http.GET
import retrofit2.http.Path

class RepositoryImpl {
    @GET("products/{id}")
    override suspend fun getProductById(@Path("id") id: Int): Product {
        //return Product(2,"Title" ,"Description")
        return Product(2,"Title", "Description" )}

    //   override suspend fun getProductById(): Product

//    {
//        return Product(
//            2,  "title", "De", "Mama", "220", "",
//            "", "", "", "")
//    }
}