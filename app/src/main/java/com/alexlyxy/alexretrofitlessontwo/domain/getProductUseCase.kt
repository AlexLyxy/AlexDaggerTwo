package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

//class GetProductUseCase(private val productRepository: ProductRepositoryImpl) {

//    suspend fun getLatestProduct(): Response<Product> {
//        return productRepository.getLatestProduct(0)
//    }
//    suspend fun getLocalProduct(): Response<Product> {
//        return productRepository.getLocalProduct(id = 0)
//    }
//}

class GetProductUseCase(private val productApi: ProductApi) {

    sealed class Result {
        class Success(val products: Product) : Result()
        object Failure: Result()
    }

    suspend fun getLatestProduct(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getProduct(2)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
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

    suspend fun getLocalProduct(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getProduct(2)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
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
