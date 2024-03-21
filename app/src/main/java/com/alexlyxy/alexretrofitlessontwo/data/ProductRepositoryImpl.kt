package com.alexlyxy.alexretrofitlessontwo.data

import com.alexlyxy.alexretrofitlessontwo.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepositoryImpl {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    override suspend fun getLatestProduct(id: Int): Product {
        return productApi.getProduct(8)
    }

//    override suspend fun getLocalProduct(id: Int): Response<Product> {
//        return Product (
//            id = 2,
//            title = "Title",
//            description = "Description",
//            price = 549,
//            discountPercentage = 12.96,
//            rating = 4.69,
//            stock = 94,
//            brand = "Brand",
//            category = "Category",
//            thumbnail = "Thumb",
//            images = arrayListOf(
//                "",
//                "https://cdn.dummyjson.com/product-images/8/1.jpg",
//                "https://cdn.dummyjson.com/product-images/8/2.jpg",
//                "https://cdn.dummyjson.com/product-images/8/3.jpg"
//            )
//        )
//    }
}