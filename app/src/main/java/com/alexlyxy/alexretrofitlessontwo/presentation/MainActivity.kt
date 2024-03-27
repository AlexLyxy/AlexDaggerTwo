package com.alexlyxy.alexretrofitlessontwo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import com.alexlyxy.alexretrofitlessontwo.presentation.common.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.presentation.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.presentation.common.viewsmvc.ScreensNavigator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ListViewMvc.Listener {

    //private val repository = ProductRepositoryImpl()
    //private var getProductUseCase = GetProductUseCase(repository)

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    private lateinit var viewMvc: ListViewMvc

    private lateinit var getProductUseCase: GetProductUseCase

    private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var screensNavigator: ScreensNavigator

    private lateinit var binding: ActivityMainBinding

    private lateinit var productApi: ProductApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = ListViewMvc(LayoutInflater.from(this), null)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //setContentView(viewMvc.rootView)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productApi: ProductApi = retrofit.create(ProductApi::class.java)

        //getProductUseCase = GetProductUseCase(productApi)

        //getProductUseCase = (application as MyApplication).getProductUseCase

        dialogsNavigator = DialogsNavigator(supportFragmentManager)

        screensNavigator = ScreensNavigator(this)

        fetchProduct()

        binding.button.setOnClickListener {

            //fetchStart()
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                //val product = getProductUseCase.getLocalProduct()
                val product = productApi.getProduct(8)


                Log.d("MyLog", "Product : $product")
                //Log.d("MyLog", "Response : ${getProductUseCase.getLatestProduct()}")

//                runOnUiThread {
//                    //fetchStop()
//                    binding.apply {
//
//                        tvTitle.text = buildString {
//                            append("Title:  ")
//                            append(product.body()?.title)
//                        }
//                        tvDescr.text = buildString {
//                            append("Description:  ")
//                            append(product.body()?.description)
//                        }
//                        tvPrice.text = buildString {
//                            append("Price:  ")
//                            append(product.body()?.price)
//                        }
//                        tvDiscount.text = buildString {
//                            append("DiscountPercentage:  ")
//                            append(product.body()?.discountPercentage)
//                        }
//                        tvRating.text = buildString {
//                            append("Rating:  ")
//                            append(product.body()?.rating)
//                        }
//                        tvStock.text = buildString {
//                            append("Stock:  ")
//                            append(product.body()?.stock)
//                        }
//                        tvBrand.text = buildString {
//                            append("Brand:  ")
//                            append(product.body()?.brand)
//                        }
//                        tvCategory.text = buildString {
//                            append("Category:  ")
//                            append(product.body()?.category)
//                        }
//                        tvThumbnail.text = buildString {
//                            append("Thumbnail:  ")
//                            append(product.body()?.thumbnail)
//                        }
//                        Picasso.get().load(product.body()!!.images[1]).into(ivImageOne)
//                        Picasso.get().load(product.body()!!.images[2]).into(ivImageTwo)
//                        Picasso.get().load(product.body()!!.images[3]).into(ivImageThree)
//                    }
//                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchProduct()
            fetchStart()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unregisterListener(this)
        fetchStop()
    }

    override fun onRefreshClicked() {
        fetchProduct()
    }

    private fun fetchStart() {
        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
        progressBarCircus.visibility = View.VISIBLE
    }

    private fun fetchStop() {
        val progressBarCircus: ProgressBar = findViewById(R.id.progressBarCircus)
        progressBarCircus.visibility = View.INVISIBLE
    }

    private fun fetchProduct() {
        coroutineScope.launch {
            delay(5000)
            viewMvc.showProgressIndication()
            delay(1000)
            try {
                //val response = getProductUseCase.getProduct()
                // if (response.isSuccessful && response.body() != null) {
                //when (val response = getProductUseCase.getLatestProduct()) {
                    when (val response = productApi.getProduct(2)) {
                    //is GetProductUseCase.Result.Success -> {
                        is ProductApi.Result.Success -> {
                        isDataLoaded = true
                        Log.d("MyLog", "Response : ${response.products.description}")
                        binding.apply {
                            tvTitle.text = buildString {
                                append("Title:  ")
                                append(response.products.title)
                            }
                            tvDescr.text = buildString {
                                append("Description:  ")
                                append(response.products.description)
                            }
                            tvPrice.text = buildString {
                                append("Price:  ")
                                append(response.products.price)
                            }
                            tvDiscount.text = buildString {
                                append("DiscountPercentage:  ")
                                append(response.products.discountPercentage)
                            }
                            tvRating.text = buildString {
                                append("Rating:  ")
                                append(response.products.rating)
                            }
                            tvStock.text = buildString {
                                append("Stock:  ")
                                append(response.products.stock)
                            }
                            tvBrand.text = buildString {
                                append("Brand:  ")
                                append(response.products.brand)
                            }
                            tvCategory.text = buildString {
                                append("Category:  ")
                                append(response.products.category)
                            }
                            tvThumbnail.text = buildString {
                                append("Thumbnail:  ")
                                append(response.products.thumbnail)
                            }
                            Picasso.get().load(response.products.images[1]).into(ivImageOne)
                            Picasso.get().load(response.products.images[2]).into(ivImageTwo)
                            Picasso.get().load(response.products.images[3]).into(ivImageThree)
                        }
                    }

                    is GetProductUseCase.Result.Failure -> onFetchFailed()
                }
            } finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}






