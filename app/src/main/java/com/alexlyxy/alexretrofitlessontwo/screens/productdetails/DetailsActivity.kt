package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.Constants
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity(), ProductDetailsViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var productApi: ProductApi

    private lateinit var viewMvc: ProductDetailsViewMvc

//    private lateinit var toolbar: MyToolbar
//    private lateinit var swipeRefresh: SwipeRefreshLayout
//    private lateinit var textProduct: TextView
//    private lateinit var pictureProduct: ImageView
    //private var isDataLoaded = false

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = ProductDetailsViewMvc(LayoutInflater.from(this), null)

        setContentView(viewMvc.rootView)

        // init retrofit

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductApi::class.java)

        //retrieve question ID passed from outside
        productId = intent.extras!!.getInt(EXTRA_PRODUCT_ID)
        Log.d("MyLog", "ProductIDdetails : $productId")
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        fetchProductDetails()
        Toast.makeText(applicationContext, "ActivityDetailsOnStart", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unregisterListener(this)
    }

    private fun fetchProductDetails() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                val response = productApi.getAllProduct("")
                if (response.isSuccessful && response.body() != null) {
                    val detailsBody = response.body()!!.products[productId - 1]
                    val detailsBodyPicture = response.body()!!.products[productId - 1].images[0]

                    Log.d("MyLog", "Details BodyDescr : $detailsBody")
                    Log.d("MyLog", "Details BodyPicture : $detailsBodyPicture")

                    viewMvc.bindProductBody(detailsBody)

                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
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

    override fun onBackClicked() {
        onBackPressed()
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


