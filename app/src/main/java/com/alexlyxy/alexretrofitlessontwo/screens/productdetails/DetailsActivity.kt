package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.ContentValues.TAG
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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.R.id.ivImageOne
import com.alexlyxy.alexretrofitlessontwo.R.id.toolbar
import com.alexlyxy.alexretrofitlessontwo.R.id.tvDescr
import com.alexlyxy.alexretrofitlessontwo.R.id.tvPrice
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityDetailsSmallBinding
import com.alexlyxy.alexretrofitlessontwo.databinding.ProductDetailsItemBinding
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.products.ProductDetailsModel
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.common.toolbar.MyToolbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var binding: ActivityDetailsSmallBinding

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var txtDetailsBody: TextView

    private lateinit var productApi: ProductApi

    private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityDetailsSmallBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.layout_product_details)
        txtDetailsBody = findViewById(tvDescr)
        // txtDetailsBody =

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
                // val response = productApi.getProduct(productId)
                if (response.isSuccessful && response.body() != null) {
                    val detailsBody = response.body()!!.products[productId - 1].images[0]
                    Log.d("MyLog", "Details Body1 : $detailsBody")

//                    fun ImageView.load(path: String, request: (RequestCreator) -> RequestCreator) {
//                        request(getContext().picasso.load(detailsBody)).into(this)    }

                    val picasso = Picasso.get().load(detailsBody).into( ivImageOne)
                    Log.d("MyLog", "Picasso : $picasso")

//                    fun RequestCreator.into(
//                        ivImageOne: ImageView,
//                        func: Callback.() -> Unit
//                    ) {
//                        val callback = Callback()
//                        callback.func()
//                        into(ivImageOne, callback)
//                    }

//                    Picasso.get()
//                        .load(detailsBody)
//                        .into(ivImageOne, object : Callback {
//                            override fun onSuccess() {
//                                Log.d(TAG, "success")
//                            }
//
//                            override fun onError(e: Exception?) {
//                                Log.d(TAG, "error")
//                            }
//                        })

//                    Picasso.get().load(detailsBody).into(
//                        ivImageOne,
//                        object : Callback {
//                            override fun onSuccess() {
//                            }
//                            override fun onError() {
//                                // empty method. (looks ugly)
//                            }
//                        })

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtDetailsBody.text = Html.fromHtml(detailsBody, Html.FROM_HTML_MODE_LEGACY)
                        Picasso.get().load(detailsBody).into(ivImageOne)
                    } else {
                        @Suppress("DEPRECATION")
                        txtDetailsBody.text = Html.fromHtml(detailsBody)
                    }
                    Log.d("MyLog", "Details Body2 : $txtDetailsBody")

                    //isDataLoaded = true
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

private fun RequestCreator?.into(ivImageOne: Int)  {
    into(ivImageOne)
}

//private fun RequestCreator.into(imageView: ImageView,  func: Callback.() -> Unit) {
//    val callback = Callback()
//   callback.func()
//    into(imageView, callback)
//}

//class Callback : Callback {
//    private var _onSuccess: (() -> Unit)? = null
//    private var _onError: (() -> Unit)? = null
//    override fun onSuccess() {
//        _onSuccess?.invoke()
//    }
//
//    override fun onError(e: java.lang.Exception?) {
//        _onError?.invoke()
//    }
//}

//private fun RequestCreator.into(ivImageOne: ImageView,
//    func: Callback.() -> Unit ) {
//    val callback = Callback()
//    callback.func()
//    into(ivImageOne, callback)
//}

