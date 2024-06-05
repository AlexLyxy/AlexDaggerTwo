package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Html.fromHtml
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.R.id.ivImageOne
import com.alexlyxy.alexretrofitlessontwo.R.id.tvBrand
import com.alexlyxy.alexretrofitlessontwo.R.id.tvCategory
import com.alexlyxy.alexretrofitlessontwo.R.id.tvDescr
import com.alexlyxy.alexretrofitlessontwo.R.id.tvDiscount
import com.alexlyxy.alexretrofitlessontwo.R.id.tvPrice
import com.alexlyxy.alexretrofitlessontwo.R.id.tvRating
import com.alexlyxy.alexretrofitlessontwo.R.id.tvStock
import com.alexlyxy.alexretrofitlessontwo.R.id.tvThumbnail
import com.alexlyxy.alexretrofitlessontwo.R.id.tvTitle
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
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

    private lateinit var productTitle: TextView
    private lateinit var productDescription: TextView
    private lateinit var productCategory: TextView
    private lateinit var productPrice: TextView
    private lateinit var productDiscountPercentage: TextView
    private lateinit var productRating: TextView
    private lateinit var productStock: TextView
    private lateinit var productBrand: TextView
    private lateinit var productPicture: ImageView
    private lateinit var productThumbnail: TextView

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_product_details)

        productTitle = findViewById(tvTitle)
        productDescription = findViewById(tvDescr)
        productCategory = findViewById(tvCategory)
        productPrice = findViewById(tvPrice)
        productDiscountPercentage = findViewById(tvDiscount)
        productRating = findViewById(tvRating)
        productStock = findViewById(tvStock)
        productBrand = findViewById(tvBrand)
        productPicture = findViewById(ivImageOne)
        productThumbnail = findViewById(tvThumbnail)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { onBackPressed() }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

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
                //val responseProduct = productApi.getProduct("".toInt())
                if (response.isSuccessful && response.body() != null)  {
                    val detailsBody = response.body()!!.products[productId - 1]
                    val detailsBodyPicture = response.body()!!.products[productId - 1].images[0]
                    //val productBodyId = responseProduct.body()!!.productSingle
                    Log.d("MyLog", "Details BodyDescr : $detailsBody")
                    Log.d("MyLog", "Details BodyPicture : $detailsBodyPicture")

                    productTitle.text = fromHtml("TITLE: " + detailsBody.title, FROM_HTML_MODE_LEGACY)
                    productDescription.text = fromHtml("DESCRIPTION: " +  detailsBody.description, FROM_HTML_MODE_LEGACY)
                    productPrice.text = fromHtml("PRICE: " + detailsBody.price.toString(), FROM_HTML_MODE_LEGACY)
                    productDiscountPercentage.text = fromHtml("DISCOUNT PERCENTAGE: " +  detailsBody.discountPercentage.toString(), FROM_HTML_MODE_LEGACY)
                    productRating.text = fromHtml("RATING: " + detailsBody.rating.toString(), FROM_HTML_MODE_LEGACY)
                    productStock.text = fromHtml("STOCK: " + detailsBody.stock.toString(), FROM_HTML_MODE_LEGACY)
                    productBrand.text = fromHtml("BRAND: " + detailsBody.brand, FROM_HTML_MODE_LEGACY)
                    productCategory.text = fromHtml("CATEGORY: " + detailsBody.category, FROM_HTML_MODE_LEGACY)
                    productThumbnail.text = fromHtml("THUMBNAIL: " + detailsBody.thumbnail, FROM_HTML_MODE_LEGACY)
                    Picasso.get().load(detailsBodyPicture).into(productPicture)

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

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun start(context: Context, productId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }
}

