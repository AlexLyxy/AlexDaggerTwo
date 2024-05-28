package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityProductBinding
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.Product
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
    private lateinit var pctProductBodyPicture: TextView

    private lateinit var productApi: ProductApi

    private var productId by Delegates.notNull<Int>()

    private var itemProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_details_small)
        setContentView(binding.root)

       fun bind (item: Product) = with((binding) ) {
           itemProduct = item

           Picasso.get().load(itemProduct!!.images[0]).into(iv)


           txtProductBodyTitle = findViewById(R.id.tvTitle)
           txtProductBodyDescr = findViewById(R.id.tvDesr)
           txtProductBodyPrice = findViewById(R.id.tvPrice)
           txtProductBodyDiscount = findViewById(R.id.tvDiscount)
           txtProductBodyRating = findViewById(R.id.tvRating)
           txtProductBodyStock = findViewById(R.id.tvStock)
           txtProductBodyBrand = findViewById(R.id.tvBrand)
           txtProductBodyCategory = findViewById(R.id.tvCategory)
           txtProductBodyThumbnail = findViewById(R.id.tvThumbnail)
           // pctProductBodyPicture = findViewById(R.id.ivImageOne)

       }


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
                //val response = productApi.getProduct(productId)
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
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
                    //Picasso.get().load(productPicture).into(iv)

                    //Log.d("MyLog", "AllProductDetails: $response")
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

    companion object {
        const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun start(context: Context, productId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }
}
