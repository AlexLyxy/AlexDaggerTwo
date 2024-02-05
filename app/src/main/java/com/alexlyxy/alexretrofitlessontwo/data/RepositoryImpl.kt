package com.alexlyxy.alexretrofitlessontwo.data

import com.alexlyxy.alexretrofitlessontwo.domain.Product
import com.alexlyxy.alexretrofitlessontwo.domain.Repository
import retrofit2.http.GET
import retrofit2.http.Path

class RepositoryImpl : Repository {

    @GET("products/{id}")
   override suspend fun getProductById(@Path("id") id: Int): Product {
       return Product(id)
    }


    //{
//        return Product(1, "", "","", "",
//            "","","","","")
//    }
}