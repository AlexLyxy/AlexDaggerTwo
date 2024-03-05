package com.alexlyxy.alexretrofitlessontwo.domain

import android.provider.SyncStateContract
import android.provider.SyncStateContract.Constants
import com.alexlyxy.alexretrofitlessontwo.data.Product
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CancellationException

//class GetProductByIdUseCase (private val repository: Repository) {
//     suspend fun  getProduct(): Product {
//        return  repository.getProductById(2)
//   }


//Log.d("MyLog", "Product: $product")

class GetProductUseCase {

    sealed class Result {
        class Success(val product : Product): Result()
        object Failure : Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl( "https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val productApi: ProductApi = retrofit.create(ProductApi::class.java)

    suspend fun getLatestProduct(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = productApi.getProductById(2)
                return@withContext Result.Success(response)
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
