package com.alexlyxy.alexdaggertwo.screens.productdetails

import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexdaggertwo.R
import com.alexlyxy.alexdaggertwo.products.Product
import com.alexlyxy.alexdaggertwo.screens.commonScreens.imageloader.ImageLoader
import com.alexlyxy.alexdaggertwo.screens.commonScreens.toolbar.MyToolbar
import com.alexlyxy.alexdaggertwo.screens.commonScreens.viewsmvs.BaseViewMvc
import com.squareup.picasso.Picasso

class ProductDetailsViewMvc(
    layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    parent: ViewGroup?
) : BaseViewMvc<ProductDetailsViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.layout_product_details
) {

    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private var textProductBody: TextView
    private var pictureProductBody: ImageView
    private var textProductBodyGlide: TextView
    private var pictureProductBodyGlide: ImageView

    init {
        textProductBody = findViewById(R.id.tvTextProduct)
        pictureProductBody = findViewById(R.id.ivImageOne)

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

        textProductBodyGlide = findViewById(R.id.tvTextProductGlide)
        pictureProductBodyGlide = findViewById(R.id.ivImageOneGlide)

    }

    fun bindProductBody(productBody: Product, pictureBody: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textProductBody.text = Html.fromHtml(productBody.toString(), Html.FROM_HTML_MODE_LEGACY)
            Picasso.get().load(pictureBody).into(pictureProductBody)

            Log.d("MyLog", "Picture : $pictureBody")
        } else {
            @Suppress("DEPRECATION")
            textProductBody.text = Html.fromHtml(productBody.toString())
        }
        imageLoader.loadImage(productBody.images[0],pictureProductBodyGlide)
        textProductBodyGlide.text = productBody.title
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