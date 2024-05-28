package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityProductBinding
import com.alexlyxy.alexretrofitlessontwo.databinding.ProductDetailsItemBinding
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.products.ProductDetailsModel
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.common.toolbar.MyToolbar
import com.alexlyxy.alexretrofitlessontwo.screens.product.ProductActivity
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsDetailsAdapter: ProductsDetailsAdapter

    private lateinit var binding: ActivityProductBinding

    private lateinit var txtProductBodyTitle: TextView
    private lateinit var txtProductBodyDescr: TextView
    private lateinit var txtProductBodyPrice: TextView
    private lateinit var txtProductBodyDiscount: TextView
    private lateinit var txtProductBodyRating: TextView
    private lateinit var txtProductBodyStock: TextView
    private lateinit var txtProductBodyBrand: TextView
    private lateinit var txtProductBodyCategory: TextView
    private lateinit var txtProductBodyThumbnail: TextView
    private lateinit var pctProductBodyPicture: ImageView

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    private lateinit var itemProduct: ProductDetailsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_details_small)
        setContentView(binding.root)

       fun bind (item: ProductDetailsModel) = with((binding) ) {
           itemProduct = item

//           tvT = item.title
//           txtProductBodyDescr.text = item.description
//           txtProductBodyCategory.text = item.category
//           txtProductBodyPrice.text = item.price.toString()
//           txtProductBodyDiscount.text = item.discountPercentage.toString()
//           txtProductBodyRating.text = item.rating.toString()
//           txtProductBodyStock.text =item.stock.toString()
//           txtProductBodyBrand.text = item.brand
//           pctProductBodyPicture.id= item.images[0].toInt()
//           txtProductBodyThumbnail.text = item.thumbnail.toString()
           //Picasso.get().load(itemProduct!!.images[0]).into(i)
           // pctProductBodyPicture = findViewById(R.id.ivImageOne)
       }

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { onBackPressed() }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

        // init recycler view
        recyclerView = findViewById(R.id.recyclerDetails)
        recyclerView.layoutManager = LinearLayoutManager(this@DetailsActivity)
        productsDetailsAdapter = ProductsDetailsAdapter{ clickedProduct ->
            Toast.makeText(applicationContext, "ActivityStart", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "ActivityStartAgain", Toast.LENGTH_LONG).show()
            Log.d("MyLog", "ResponseIDproductActiviyt : ${clickedProduct.id}")
            // Log.d("MyLog", "ResponseTitleProductActiviy : ${clickedProduct.description}")
            clickedProduct.id?.let { start(this, it) }
          // clickedProduct.id?.let { start(this, it) }
        }
        recyclerView.adapter = productsDetailsAdapter

        // init retrofit

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Dear Vasilij. This the  line of App's Crashing. If I comment
        // everything connecting with Retrofit, DetailedActivity opens

        productApi = retrofit.create(ProductApi::class.java)

        productId = 0

        //retrieve question ID passed from outside
        productId = intent.extras!!.getInt(EXTRA_PRODUCT_ID)
        Log.d("MyLog", "ProductIDdetails : $productId")
    }

    override fun onStart() {
        super.onStart()
        fetchProductDetails()
        Toast.makeText(applicationContext, "ActivityStart", Toast.LENGTH_LONG).show()
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
                if (response.isSuccessful && response.body() != null) {
                    productsDetailsAdapter.bindData(response.body()!!.products)

                    isDataLoaded = true

                    val productBodyTitle = response.body()!!.products[productId-1].title
                    val productBodyDescr = response.body()!!.products[productId-1].description
                    val productBodyPrice = response.body()!!.products[productId-1].price
                    val productBodyDiscount = response.body()!!.products[productId-1].discountPercentage
                    val productBodyRating = response.body()!!.products[productId-1].rating
                    val productBodyStock = response.body()!!.products[productId-1].stock
                    val productBodyBrand = response.body()!!.products[productId-1].brand
                    val productBodyCategory = response.body()!!.products[productId-1].category
                    val productBodyThumbnail = response.body()!!.products[productId-1].thumbnail
                    val productPicture = response.body()!!.products[productId-1].images[0]

                    Log.d("MyLog", "AllProductDetails: $response")
                    Log.d("MyLog", "ProductPicture: $productPicture")

                    txtProductBodyTitle.text = Html.fromHtml(productBodyTitle, Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyDescr.text= Html.fromHtml(productBodyDescr, Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyPrice.text = Html.fromHtml(productBodyPrice.toString(), Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyDiscount.text = Html.fromHtml(productBodyDiscount.toString(), Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyRating.text = Html.fromHtml(productBodyRating.toString(), Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyStock.text = Html.fromHtml(productBodyStock.toString(), Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyBrand.text = Html.fromHtml(productBodyBrand, Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyCategory.text = Html.fromHtml(productBodyCategory, Html.FROM_HTML_MODE_LEGACY)
                    txtProductBodyThumbnail.text = Html.fromHtml(productBodyThumbnail, Html.FROM_HTML_MODE_LEGACY)
                   //productPicture = Picasso.get().load(productPicture).fetch().toString()

                    Log.d("MyLog", "Picasso: $productPicture")

                }
            else {
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

        class ProductsDetailsAdapter(
            private val onProductClickListener: (Product) -> Unit
        ) : RecyclerView.Adapter<ProductsDetailsAdapter.ProductViewHolder>() {

            private var productsDetailsList: List<Product> = ArrayList(0)

            inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                private val binding = ProductDetailsItemBinding.bind(view)
                private var itemDetailsProduct: ProductDetailsModel? = null

//            val title: TextView = view.findViewById(R.id.tvTitleView)
//            val descr: TextView = view.findViewById(R.id.tvDescriptionView)

                fun bind(item: ProductDetailsModel) = with((binding)) {
                    itemDetailsProduct = item

                    tvTitle.text = item.title
                    tvDescr.text = item.description
                    tvCategory.text = item.category
                    tvPrice.text = item.price.toString()
                    tvDiscount.text = item.discountPercentage.toString()
                    tvRating.text = item.rating.toString()
                    tvStock.text = item.stock.toString()
                    tvBrand.text = item.brand
                    Picasso.get().load(productsDetailsList[position].images[0]).into(ivImageOne)
                    tvThumbnail.text = item.thumbnail
                }
            }

            //@SuppressLint("NotifyDataSetChanged")
            fun bindData(products: List<Product>) {
                productsDetailsList = ArrayList(products)
                notifyDataSetChanged()
                Log.d("MyLog", "productListProductActivity : $productsDetailsList")
                Log.d(
                    "Mylog", "Picasso : ${
                        Picasso.get()
                            .load("https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png")
                            .fetch()
                    }"
                )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_details_item, parent, false)
                return ProductViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

//                holder.title.text = productList[position].title
//                holder.descr.text = productList[position].description

                //Dear Vasilij. I tried to show picture using Glide & Picasso. But not work???

                //Glide.with().load(productList[position].images[0]).into(R.id.ivImageOne)
                //Picasso.get().load(productList[position].images[0]).into(R.id.ivImageOne)


                Log.d("MyLog", "PictureTitleProductActivity : ${productsDetailsList[0].title}")
                //Log.d("MyLog", "PictureDescr : ${productList[0].description}")
                // Log.d("MyLog", "PicturePicture: ${productList[position].images[0]}")

                holder.bind(
                    item = ProductDetailsModel(
                        title = productsDetailsList[position].title!!,
                        description = productsDetailsList[position].description!!,
                        category = productsDetailsList[position].category!!,
                        price = productsDetailsList[position].price!!,
                        discountPercentage = productsDetailsList[position].discountPercentage!!,
                        rating = productsDetailsList[position].rating!!,
                        stock = productsDetailsList[position].stock!!,
                        brand = productsDetailsList[position].brand!!,
                        imageOne  = productsDetailsList[position].images[0],
                        thumbnail = productsDetailsList[position].thumbnail!!
                    ))
                holder.itemView.setOnClickListener {
                    onProductClickListener.invoke(productsDetailsList[position])
                }
            }


            override fun getItemCount(): Int {
                return productsDetailsList.size
            }
        }

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun start(context: Context, productId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }
}
