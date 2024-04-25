package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.common.toolbar.MyToolbar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var txtProductBody1: TextView
    private lateinit var txtProductBody2: TextView
   // private lateinit var txtProductBody: TextView

    //private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_small)

        //start(this, productId)

        Toast.makeText(applicationContext, "onCreateDetailed", Toast.LENGTH_LONG).show()

        txtProductBody1 = findViewById(R.id.tvTitleDetails)
        txtProductBody2 = findViewById(R.id.tvDesrDetails)

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
        //productApi = retrofit.create(productApi::class.java)

        //retrieve question ID passed from outside
        //productId = intent.extras!!.getString(EXTRA_PRODUCT_ID)!!

        productId = intent.extras!!.getInt(EXTRA_PRODUCT_ID)
        Log.d("MyLog", "ProductIDdetails : $productId")

    }

    override fun onStart() {
        super.onStart()
       if (!isDataLoaded){
        fetchProductDetails()
        }
        Toast.makeText(applicationContext, "ActivityStartDetailed", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchProductDetails() {
        coroutineScope.launch {
            delay(5000)
            showProgressIndication()
            try {
                //val response = productApi.getProduct(productId)
                //val response = productApi.getAllProduct("1")

                val response = productId
                 //if (response.isSuccessful && response.body() != null) {
                if (response != null) {
                    //val productBody = response.body()!!.products.get(1).description
                    val productBody1 = response.toString()
                    val productBody2 = (response +2).toString()
//
//                    Log.d("MyLog", "AllProductDetails: $response")
                    //Log.d("MyLog", "ProductBodyDetails: $productBody")

                     //txtProductBody.text = Html.fromHtml(productBody, Html.FROM_HTML_MODE_LEGACY)

                    txtProductBody1.text = Html.fromHtml(productBody1, Html.FROM_HTML_MODE_LEGACY)
                    txtProductBody2.text = Html.fromHtml(productBody2, Html.FROM_HTML_MODE_LEGACY)

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
            Log.d("MyLog", "ProductID : $productId")
//            val applicationContext = null
//            Toast.makeText(applicationContext,"Companion Object", Toast.LENGTH_LONG).show()
        }
    }
}

