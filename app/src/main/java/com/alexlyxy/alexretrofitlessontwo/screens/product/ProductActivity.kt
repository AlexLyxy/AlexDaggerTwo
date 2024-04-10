package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class ProductActivity : AppCompatActivity() {

//    private lateinit var  getProductUseCase: GetProductUseCase
//
//    private lateinit var  getProductDetailsUseCase: GetProductDetailsUseCase

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    //private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityProductBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_product)

//        getProductUseCase = GetProductUseCase ()
//        getProductDetailsUseCase = GetProductDetailsUseCase()

        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            fetchProduct()
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productsAdapter = ProductsAdapter { clickedProduct ->
            // clickedAllProduct.products?.get("".toInt())?.let { DetailsActivity.start(this, it.id) }
            DetailsActivity.start(this, clickedProduct.id)
        }
        recyclerView.adapter = productsAdapter

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)
//
//        binding.button.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                //val product = getProductUseCase.getLatestProduct()
//                val productDetails = getProductDetailsUseCase.getLatestProductDetails()
//               // val product = productApi.getProduct(8)
//
//                Log.d("MyLog", "ProductDetails : $productDetails")
//
//                runOnUiThread {
//
//                    binding.apply {
//
//                        tvTitle.text = buildString {
//                            append("Title:  ")
//                            append(productDetails.body()?.title)
//                        }
//                        tvDescr.text = buildString {
//                            append("Description:  ")
//                            append(productDetails.body()?.description)
//                        }
//                        tvPrice.text = buildString {
//                            append("Price:  ")
//                            append(productDetails.body()?.price)
//                        }
//                        tvDiscount.text = buildString {
//                            append("DiscountPercentage:  ")
//                            append(productDetails.body()?.discountPercentage)
//                        }
//                        tvRating.text = buildString {
//                            append("Rating:  ")
//                            append(productDetails.body()?.rating)
//                        }
//                        tvStock.text = buildString {
//                            append("Stock:  ")
//                            append(productDetails.body()?.stock)
//                        }
//                        tvBrand.text = buildString {
//                            append("Brand:  ")
//                            append(productDetails.body()?.brand)
//                        }
//                        tvCategory.text = buildString {
//                            append("Category:  ")
//                            append(productDetails.body()?.category)
//                        }
//                        tvThumbnail.text = buildString {
//                            append("Thumbnail:  ")
//                            append(productDetails.body()?.thumbnail)
//                        }
//                        Picasso.get().load(productDetails.body()!!.images[1]).into(ivImageOne)
//                        Picasso.get().load(productDetails.body()!!.images[2]).into(ivImageTwo)
//                        Picasso.get().load(productDetails.body()!!.images[3]).into(ivImageThree)
//                    }
//                }
//            }
//        }
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
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        productApi = retrofit.create(ProductApi::class.java)
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = productApi.getAllProduct("")
                //val response = productApi.getProduct(2)
                //val response = getProductUseCase.getLatestProduct()
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.products?.let { productsAdapter.bindData(it) }
                    isDataLoaded = true

                    Log.d("MyLog", "Response : ${response.body()}")

//                        binding.apply {
//
//                            tvTitle.text = buildString {
//                                append("Title:  ")
//                                append(response.body()?.title)
//                            }
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
//                            Picasso.get().load(response.body()!!.images[1]).into(ivImageOne)
//                            Picasso.get().load(response.body()!!.images[2]).into(ivImageTwo)
//                            Picasso.get().load(response.body()!!.images[3]).into(ivImageThree)
//                        }

                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
                }
            } finally {
                hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    private fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    class ProductsAdapter(
        private val onProductClickListener: (Product) -> Unit
    ) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

        private var productList: List<Product> = ArrayList(0)

        inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.tvTitleView)
            val descr: TextView = view.findViewById(R.id.tvDescriptionView)
            //val picture: ImageView = view.findViewById(R.id.ivImageOne)

            // private val binding = ActivityProductBinding.bind(view)

        }

        @SuppressLint("NotifyDataSetChanged")
        fun bindData(products: List<Product>) {
            productList = ArrayList(products)

            //Log.d("MyLog", "productList : $productList")
            //Picasso.get().load("https://dummyjson.com").into(ivImageOne)
           // Picasso.get().load("https: " + products[0].images[0]).into(R.id.ivImageOne)
            //Picasso.get().load(productList["".toInt()].images["".toInt()]).into(tv)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.title.text = productList[position].title
            holder.descr.text = productList[position].description
            //holder.picture.id= productList[position].images[0].toInt()
            //Picasso.get().load(productList[position].images[0]).into(R.id.ivImageOne)
           // Picasso.get().load(productList[position].images[0]).resize(50,50).centerCrop().into(R.id.ivImageOne)

            Log.d("MyLog", "PictureTitle : ${productList[0].title}")
            Log.d("MyLog", "PictureDescr : ${productList[0].description}")
            Log.d("MyLog", "PicturePicture: ${productList[position].images[0]}")

            //holder.picture.id = productList[position].images["".toInt()].toInt()
            //Picasso.get().load(productList["".toInt()].images["".toInt()]).into(i)

            holder.itemView.setOnClickListener {
                onProductClickListener.invoke(productList[position])
            }
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }
}

private fun RequestCreator.into(ivImageOne: Any) {
    TODO("Not yet implemented")
}











