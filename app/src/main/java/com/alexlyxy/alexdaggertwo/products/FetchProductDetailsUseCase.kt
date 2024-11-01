package com.alexlyxy.alexdaggertwo.products

import com.alexlyxy.alexdaggertwo.networking.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class FetchProductDetailsUseCase @Inject constructor(private val productApi: ProductApi) {

    sealed class Result {
        class Success(val product: Product, val picture: String) : Result()
        object Failure: Result()
    }

    suspend fun fetchProduct(productId: Int): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
                    //return@withContext Result.Success(response.body()!!.products[productId])
                    return@withContext Result.Success(
                        response.body()!!.products[productId-1],
                        response.body()!!.products[productId-1].images[0])
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