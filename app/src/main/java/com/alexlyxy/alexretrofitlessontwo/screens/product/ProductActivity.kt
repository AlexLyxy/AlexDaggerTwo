package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.common.dialogs.ServerErrorDialogFragment
import com.alexlyxy.alexretrofitlessontwo.screens.common.viewsmvs.ScreensNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.productdetails.DetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), ProductActivityViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    private lateinit var viewMvc: ProductActivityViewMvc

    private lateinit var fetchProductUseCase: FetchProductUseCase

    private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = ProductActivityViewMvc(LayoutInflater.from(this), null)
        setContentView(viewMvc.rootView)

        fetchProductUseCase =
            FetchProductUseCase(((application as MyApplication).productApi))

        dialogsNavigator = DialogsNavigator(supportFragmentManager)

        screensNavigator = ScreensNavigator(this)
   }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchProduct()
            Toast.makeText(applicationContext, "ActivityProductOnStart", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unregisterListener(this)
    }

    override fun onRefreshClicked() {
        fetchProduct()
    }

    private fun fetchProduct() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                val result = fetchProductUseCase.fetchLatestProduct()
                when (result) {
                    is FetchProductUseCase.Result.Success -> {
                        viewMvc.bindProduct(result.products)
                        isDataLoaded = true
                    }
                    is FetchProductUseCase.Result.Failure -> onFetchFailed()
                }

            } finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onProductClicked(clickedProduct: Product) {
        clickedProduct.id?.let { screensNavigator.toQuestionDetails(it) }
    }
}


















