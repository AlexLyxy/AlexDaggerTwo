package com.alexlyxy.alexretrofitlessontwo.domain

import com.alexlyxy.alexretrofitlessontwo.data.Product
import com.alexlyxy.alexretrofitlessontwo.data.ProductRepositoryAppImpl
import retrofit2.Response

class GetProductAppUseCase(private val productRepository: ProductRepositoryAppImpl) {

        suspend fun getProductApp(): Response<Product> {
            return productRepository.getProductApp(0)
        }
}