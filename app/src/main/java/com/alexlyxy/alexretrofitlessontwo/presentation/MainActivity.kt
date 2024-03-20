package com.alexlyxy.alexretrofitlessontwo.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import com.alexlyxy.alexretrofitlessontwo.presentation.dialogs.ServerErrorDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : AppCompatActivity() {

    private lateinit var  getProductUseCase: GetProductUseCase

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var binding: ActivityMainBinding

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)

        getProductUseCase = GetProductUseCase ()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        binding.button.setOnClickListener {
            progressBarCircus.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {

                delay(10000)

                val product = getProductUseCase.getLatestProduct()
               // val product = productApi.getProduct(8)

                Log.d("MyLog", "Product : $product")

                runOnUiThread {
                    progressBarCircus.visibility = View.INVISIBLE
                    binding.apply {

                        tvTitle.text = buildString {
                            append("Title:  ")
                            append(product.body()?.title)
                        }
                        tvDescr.text = buildString {
                            append("Description:  ")
                            append(product.body()?.description)
                        }
                        tvPrice.text = buildString {
                            append("Price:  ")
                            append(product.body()?.price)
                        }
                        tvDiscount.text = buildString {
                            append("DiscountPercentage:  ")
                            append(product.body()?.discountPercentage)
                        }
                        tvRating.text = buildString {
                            append("Rating:  ")
                            append(product.body()?.rating)
                        }
                        tvStock.text = buildString {
                            append("Stock:  ")
                            append(product.body()?.stock)
                        }
                        tvBrand.text = buildString {
                            append("Brand:  ")
                            append(product.body()?.brand)
                        }
                        tvCategory.text = buildString {
                            append("Category:  ")
                            append(product.body()?.category)
                        }
                        tvThumbnail.text = buildString {
                            append("Thumbnail:  ")
                            append(product.body()?.thumbnail)
                        }
                        Picasso.get().load(product.body()!!.images[1]).into(ivImageOne)
                        Picasso.get().load(product.body()!!.images[2]).into(ivImageTwo)
                        Picasso.get().load(product.body()!!.images[3]).into(ivImageThree)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded) {
            fetchProduct()
            fetchStart()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        fetchStop()
    }

    private fun fetchStart (){
        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
        progressBarCircus.visibility = View.VISIBLE
    }

    private fun fetchStop (){
        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
        progressBarCircus.visibility = View.INVISIBLE
    }

    private fun fetchProduct() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        coroutineScope.launch {

            delay(10000)

            try {
                val response = productApi.getProduct(2)
                if (response.isSuccessful && response.body() != null) {
                    isDataLoaded = true
                    Log.d("MyLog", "Response : ${productApi.getProduct(2)}")

                        binding.apply {

                            tvTitle.text = buildString {
                                append("Title:  ")
                                append(response.body()?.title)
                            }
                            tvDescr.text = buildString {
                                append("Description:  ")
                                append(response.body()?.description)
                            }
                            tvPrice.text = buildString {
                                append("Price:  ")
                                append(response.body()?.price)
                            }
                            tvDiscount.text = buildString {
                                append("DiscountPercentage:  ")
                                append(response.body()?.discountPercentage)
                            }
                            tvRating.text = buildString {
                                append("Rating:  ")
                                append(response.body()?.rating)
                            }
                            tvStock.text = buildString {
                                append("Stock:  ")
                                append(response.body()?.stock)
                            }
                            tvBrand.text = buildString {
                                append("Brand:  ")
                                append(response.body()?.brand)
                            }
                            tvCategory.text = buildString {
                                append("Category:  ")
                                append(response.body()?.category)
                            }
                            tvThumbnail.text = buildString {
                                append("Thumbnail:  ")
                                append(response.body()?.thumbnail)
                            }
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
                fetchStop()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}




