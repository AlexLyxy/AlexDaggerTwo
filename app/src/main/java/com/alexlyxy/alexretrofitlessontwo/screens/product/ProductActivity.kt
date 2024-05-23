package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class ProductActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            fetchProduct()
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productsAdapter = ProductsAdapter { clickedProduct ->
            Toast.makeText(applicationContext, "ActivityStart", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "ActivityStartAgain", Toast.LENGTH_LONG).show()
            Log.d("MyLog", "ResponseIDproductActiviyt : ${clickedProduct.id}")
            // Log.d("MyLog", "ResponseTitleProductActiviy : ${clickedProduct.description}")

            clickedProduct.id?.let { DetailsActivity.start(this, it) }
        }
        recyclerView.adapter = productsAdapter

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

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
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
                    productsAdapter.bindData(response.body()!!.products)
                    isDataLoaded = true

                    Log.d("MyLog", "ResponseProductActivity : $response")
                    Log.d(
                        "MyLog",
                        "ResponseBodyProductActivity[1] : ${response.body()!!.products[2]}"
                    )
                    Log.d("MyLog", "ResponseBodyProductActivity : ${response.body()!!.products}")
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
           // val image: ImageView = Picasso.get().load([position].images[0]).into(R.id.ivImageOne)
            val image  = Picasso.get().load("https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png")
                .fetch()

//            Picasso.get().load("https://cdn.pixabay.com/photo/2019/04/15/20/42/bitcoin-4130299_1280.png")
//            .into(ivCoin)
//
//            Picasso.get().load(item.dogFaceOne).into(ivDogOne)

        }

        //@SuppressLint("NotifyDataSetChanged")
        fun bindData(products: List<Product>) {
            productList = ArrayList(products)
            notifyDataSetChanged()
            Log.d("MyLog", "productListProductActivity : $productList")

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.title.text = productList[position].title
            holder.descr.text = productList[position].description
            //holder.image.id = productList[position].images[0].toInt()
            
            //Dear Vasilij. I tried to show picture using Glide & Picasso. But not work???

            //Glide.with().load(productList[position].images[0]).into(R.id.ivImageOne)
            //Picasso.get().load(productList[position].images[0]).into(R.id.ivImageOne)



            Log.d("MyLog", "PictureTitleProductActivity : ${productList[0].title}")
            //Log.d("MyLog", "PictureDescr : ${productList[0].description}")
            // Log.d("MyLog", "PicturePicture: ${productList[position].images[0]}")

            holder.itemView.setOnClickListener {
                onProductClickListener.invoke(productList[position])
            }
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }
}
//
//private fun into(ivImageOne: Int): ImageView {
//return into(R.id.ivImageOne)
//}














