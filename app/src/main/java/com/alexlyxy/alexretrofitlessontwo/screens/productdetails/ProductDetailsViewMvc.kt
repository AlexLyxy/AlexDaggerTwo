package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.screens.common.toolbar.MyToolbar
import com.alexlyxy.alexretrofitlessontwo.screens.common.viewsmvs.BaseViewMvc

class ProductDetailsViewMvc (
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): BaseViewMvc<ProductDetailsViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.layout_product_details
){

    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val textProductBody: TextView
    //private val pictureProductBody: ImageView

    init {
        textProductBody = findViewById(R.id.tvTextProduct)
       // pictureProductBody = findViewById(R.id.ivImageOne)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onBackClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun bindProductBody(productBody: Product) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textProductBody.text = Html.fromHtml(productBody.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            textProductBody.text = Html.fromHtml(productBody.toString())
        }
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }
}