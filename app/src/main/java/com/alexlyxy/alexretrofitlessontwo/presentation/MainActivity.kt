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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var  getProductUseCase:  GetProductUseCase

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductUseCase = GetProductUseCase ()

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = getProductUseCase.getLatestProduct()

                Log.d("MyLog", "Product : $product")

                runOnUiThread {

                    binding.apply {

                        tvTitle.text = buildString {
                            append("Title:  ")
                            append(product.title)
                        }
                        tvDescr.text = buildString {
                            append("Description:  ")
                            append(product.description)
                        }
                        tvPrice.text = buildString {
                            append("Price:  ")
                            append(product.price.toString())
                        }
                        tvDiscount.text = buildString {
                            append("DiscountPercentage:  ")
                            append(product.discountPercentage.toString())
                        }
                        tvRating.text = buildString {
                            append("Rating:  ")
                            append(product.rating.toString())
                        }
                        tvStock.text = buildString {
                            append("Stock:  ")
                            append(product.stock.toString())
                        }
                        tvBrand.text = buildString {
                            append("Brand:  ")
                            append(product.brand)
                        }
                        tvCategory.text = buildString {
                            append("Category:  ")
                            append(product.category)
                        }
                        tvThumbnail.text = buildString {
                            append("Thumbnail:  ")
                            append(product.thumbnail)
                        }
                        Picasso.get().load(product.images[1]).into(ivImageOne)
                        Picasso.get().load(product.images[2]).into(ivImageTwo)
                        Picasso.get().load(product.images[3]).into(ivImageThree)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded) {
            fetchProduct()
            //fetchStart()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
       //fetchStop()
    }

//    private fun fetchStart() {
//        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
//        progressBarCircus.visibility = View.VISIBLE
//    }
//
//    private fun fetchStop() {
//        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
//        progressBarCircus.visibility = View.INVISIBLE
//    }

    private fun fetchProduct() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        coroutineScope.launch {

            delay(1000)
            try {
                //val response = getProductUseCase.getLatestProduct()
                val response = getProductUseCase.getProduct()

                if (response.isSuccessful && response.body() != null) {
                    isDataLoaded = true
                    Log.d("MyLog", "ResponseApp : ${productApi.getProduct(3)}")

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
