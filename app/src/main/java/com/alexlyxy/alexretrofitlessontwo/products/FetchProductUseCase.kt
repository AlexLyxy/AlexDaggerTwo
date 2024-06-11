package com.alexlyxy.alexretrofitlessontwo.products

import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import kotlin.coroutines.cancellation.CancellationException

class FetchProductUseCase (private val productApi: ProductApi) {

    sealed class Result {
        class Success(val products: List<Product>) : Result()
        object Failure: Result()
    }

    suspend fun fetchLatestProduct(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.products)
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