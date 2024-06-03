package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.R.id.toolbar
import com.alexlyxy.alexretrofitlessontwo.R.id.tvDescr
import com.alexlyxy.alexretrofitlessontwo.databinding.ProductDetailsItemBinding
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.products.ProductDetailsModel
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.common.toolbar.MyToolbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var txtDetailsBody: TextView

    //private lateinit var recyclerView: RecyclerView
    //private lateinit var productsDetailsAdapter: ProductsDetailsAdapter

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_details_small)
        setContentView(R.layout.layout_product_details)

        txtDetailsBody = findViewById(R.id.tvDescr)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { onBackPressed() }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

//        // init recycler view
//        recyclerView = findViewById(R.id.recyclerDetails)
//        recyclerView.layoutManager = LinearLayoutManager(this@DetailsActivity)
//        productsDetailsAdapter = ProductsDetailsAdapter { clickedProduct ->
//            Toast.makeText(applicationContext, "ActivityDetaisRecyclerStart", Toast.LENGTH_LONG).show()
//            //Toast.makeText(applicationContext, "ActivityStartAgain", Toast.LENGTH_LONG).show()
//            Log.d("MyLog", "ResponseIDproductActiviyt : ${clickedProduct.id}")
//            clickedProduct.id?.let { start(this, it) }
//        }
//        recyclerView.adapter = productsDetailsAdapter

        // init retrofit

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        productId = 0

        //retrieve question ID passed from outside
        productId = intent.extras!!.getInt(EXTRA_PRODUCT_ID)
        Log.d("MyLog", "ProductIDdetails : $productId")
    }

    override fun onStart() {
        super.onStart()
        fetchProductDetails()
        Toast.makeText(applicationContext, "ActivityDetailsOnStart", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchProductDetails() {
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = productApi.getAllProduct("")
               // val response = productApi.getProduct(productId)
                if (response.isSuccessful && response.body() != null) {
                val detailsBody = response.body()!!.products[productId-1].description

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtDetailsBody.text = Html.fromHtml(detailsBody.toString(), Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        @Suppress("DEPRECATION")
                        txtDetailsBody.text = Html.fromHtml(detailsBody.toString())
                    }
                    //productsDetailsAdapter.bindData(response.body()!!.products.subList(productId-1,productId))
                   // productsDetailsAdapter.bindData(responseProduct.body()!!.products)
                    Log.d("MyLog", "Details Body : $detailsBody")

                    isDataLoaded = true
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
        swipeRefresh.isRefreshing = false
    }
//
//    class ProductsDetailsAdapter(
//        private val onProductClickListener: (Product) -> Unit
//    ) : RecyclerView.Adapter<ProductsDetailsAdapter.ProductViewHolder>() {
//
//        //private var productsDetailsList: List<ProductWithBody> = ArrayList(0)
//        private var productsDetailsList: List<Product> = ArrayList(0)
//
//        inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            private val binding = ProductDetailsItemBinding.bind(view)
//            private var itemDetailsProduct: ProductDetailsModel? = null
//
////            val title: TextView = view.findViewById(R.id.tvTitleView)
////            val descr: TextView = view.findViewById(R.id.tvDescriptionView)
//
//            fun bind(item: ProductDetailsModel) = with((binding)) {
//                itemDetailsProduct = item
//
//                tvTitle.text = buildString {
//                    append(" TITLE : ")
//                    append(item.title)
//                }
//                tvDescr.text =  buildString {
//                    append("      DESCRIPTION : ")
//                    append(item.description)
//                }
//                tvCategory.text =
//                    buildString {
//                        append(" CATEGORY : ")
//                        append(item.category)
//                    }
//                tvPrice.text =  buildString {
//                    append(" PRICE : ")
//                    append(item.price)
//                }
//                tvDiscount.text =  buildString {
//                    append(" DISCOUNT PERCENTAGE : ")
//                    append(item.discountPercentage)
//                }
//                tvRating.text =  buildString {
//                    append(" RATING : ")
//                    append(item.rating)
//                }
//                tvStock.text = buildString {
//                    append(" STOCK : ")
//                    append(item.stock)
//                }
//                tvBrand.text = buildString {
//                    append(" BRAND : ")
//                    append(item.brand)
//                }
//                Picasso.get().load(productsDetailsList[position].images[0]).into(ivImageOneDetails)
//                tvThumbnail.text = buildString {
//                    append(" THUMBNAIL : ")
//                    append(item.thumbnail)
//                }
//            }
//        }
//
//        //@SuppressLint("NotifyDataSetChanged")
//        fun bindData(product: List<Product>) {
//            productsDetailsList = ArrayList(product)
//            notifyDataSetChanged()
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//            val itemView = LayoutInflater.from(parent.context)
//                .inflate(R.layout.product_details_item, parent, false)
//            return ProductViewHolder(itemView)
//        }
//
//        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//
////               holder.title.text = productsDetailsList[position].title
////                holder.descr.text = productList[position].description
//
//            //Glide.with().load(productList[position].images[0]).into(R.id.ivImageOne)
//            //Picasso.get().load(productList[position].images[0]).into(R.id.ivImageOne)
//
//            holder.bind(
//                item = ProductDetailsModel(
//                    title = productsDetailsList[position].title!!,
//                    description = productsDetailsList[position].description!!,
//                    category = productsDetailsList[position].category!!,
//                    price = productsDetailsList[position].price!!,
//                    discountPercentage = productsDetailsList[position].discountPercentage!!,
//                    rating = productsDetailsList[position].rating!!,
//                    stock = productsDetailsList[position].stock!!,
//                    brand = productsDetailsList[position].brand!!,
//                    imageOneDetails = productsDetailsList[position].images[0],
//                    thumbnail = productsDetailsList[position].thumbnail!!
//                )
//            )
//
//            holder.itemView.setOnClickListener {
//                onProductClickListener.invoke(productsDetailsList[position])
//            }
//        }
//
//        override fun getItemCount(): Int {
//            return productsDetailsList.size
//        }
//    }

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun start(context: Context, productId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }
}
