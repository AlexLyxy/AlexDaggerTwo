package com.alexlyxy.alexretrofitlessontwo.screens.productdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ScreensNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DetailsActivity : BaseActivity(), ProductDetailsViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var viewMvc: ProductDetailsViewMvc

    private lateinit var fetchProductDetailsUseCase: FetchProductDetailsUseCase

    private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var screensNavigator: ScreensNavigator

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = ProductDetailsViewMvc(LayoutInflater.from(this), null)
        //Parent = null for Activityy, but for Fragment not
        setContentView(viewMvc.rootView)

        fetchProductDetailsUseCase = compositionRoot.fetchProductDetailsUseCase

        dialogsNavigator = compositionRoot.dialogsNavigator

        screensNavigator = compositionRoot.screensNavigator

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
                val result = fetchProductDetailsUseCase.fetchProduct(productId)
                when (result) {
                    is FetchProductDetailsUseCase.Result.Success -> {
                        viewMvc.bindProductBody(result.product, result.picture)
                    }

                    is FetchProductDetailsUseCase.Result.Failure -> onFetchFailed()
                }

            } finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onBackClicked() {
        screensNavigator.navigateBack()
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


