package com.alexlyxy.alexretrofitlessontwo.products

import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class FetchProductDetailsUseCase {

    sealed class Result {
        class Success(val product: Product) : Result()
        object Failure: Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    suspend fun fetchProduct(productId: Int): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.products[productId])
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }
}