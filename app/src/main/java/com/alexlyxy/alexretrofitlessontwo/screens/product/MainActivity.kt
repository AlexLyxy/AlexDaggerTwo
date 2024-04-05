package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.alexlyxy.alexretrofitlessontwo.products.GetProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.products.GetProductUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : AppCompatActivity() {

    private lateinit var  getProductUseCase: GetProductUseCase

    private lateinit var  getProductDetailsUseCase: GetProductDetailsUseCase

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var binding: ActivityMainBinding

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductUseCase = GetProductUseCase ()

        getProductDetailsUseCase = GetProductDetailsUseCase()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //val product = getProductUseCase.getLatestProduct()
                val productDetails = getProductDetailsUseCase.getLatestProductDetails()
               // val product = productApi.getProduct(8)

                Log.d("MyLog", "ProductDetails : $productDetails")

                runOnUiThread {

                    binding.apply {

                        tvTitle.text = buildString {
                            append("Title:  ")
                            append(productDetails.body()?.title)
                        }
                        tvDescr.text = buildString {
                            append("Description:  ")
                            append(productDetails.body()?.description)
                        }
                        tvPrice.text = buildString {
                            append("Price:  ")
                            append(productDetails.body()?.price)
                        }
                        tvDiscount.text = buildString {
                            append("DiscountPercentage:  ")
                            append(productDetails.body()?.discountPercentage)
                        }
                        tvRating.text = buildString {
                            append("Rating:  ")
                            append(productDetails.body()?.rating)
                        }
                        tvStock.text = buildString {
                            append("Stock:  ")
                            append(productDetails.body()?.stock)
                        }
                        tvBrand.text = buildString {
                            append("Brand:  ")
                            append(productDetails.body()?.brand)
                        }
                        tvCategory.text = buildString {
                            append("Category:  ")
                            append(productDetails.body()?.category)
                        }
                        tvThumbnail.text = buildString {
                            append("Thumbnail:  ")
                            append(productDetails.body()?.thumbnail)
                        }
                        Picasso.get().load(productDetails.body()!!.images[1]).into(ivImageOne)
                        Picasso.get().load(productDetails.body()!!.images[2]).into(ivImageTwo)
                        Picasso.get().load(productDetails.body()!!.images[3]).into(ivImageThree)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded) {
            fetchProduct()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchProduct() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        coroutineScope.launch {
            try {
                //val response = productApi.getProduct(2)
                val response = getProductUseCase.getLatestProduct()
                if (response.isSuccessful && response.body() != null) {
                    isDataLoaded = true
                    Log.d("MyLog", "Response : ${productApi.getProduct(2)}")

                        binding.apply {

                            tvTitle.text = buildString {
                                append("Title:  ")
                                append(response.body()?.title)
                            }
//                            tvDescr.text = buildString {
//                                append("Description:  ")
//                                append(response.body()?.description)
//                            }
//                            tvPrice.text = buildString {
//                                append("Price:  ")
//                                append(response.body()?.price)
//                            }
//                            tvDiscount.text = buildString {
//                                append("DiscountPercentage:  ")
//                                append(response.body()?.discountPercentage)
//                            }
//                            tvRating.text = buildString {
//                                append("Rating:  ")
//                                append(response.body()?.rating)
//                            }
//                            tvStock.text = buildString {
//                                append("Stock:  ")
//                                append(response.body()?.stock)
//                            }
//                            tvBrand.text = buildString {
//                                append("Brand:  ")
//                                append(response.body()?.brand)
//                            }
//                            tvCategory.text = buildString {
//                                append("Category:  ")
//                                append(response.body()?.category)
//                            }
//                            tvThumbnail.text = buildString {
//                                append("Thumbnail:  ")
//                                append(response.body()?.thumbnail)
//                            }
                            Picasso.get().load(response.body()!!.images[1]).into(ivImageOne)
                            Picasso.get().load(response.body()!!.images[2]).into(ivImageTwo)
                            Picasso.get().load(response.body()!!.images[3]).into(ivImageThree)
                        }

                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
                }
            } finally {

            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}




