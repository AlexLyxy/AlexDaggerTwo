package com.alexlyxy.alexdaggertwo.screens.productdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alexlyxy.alexdaggertwo.products.FetchProductDetailsUseCase
import com.alexlyxy.alexdaggertwo.screens.commonScreens.ScreensNavigator
import com.alexlyxy.alexdaggertwo.screens.commonScreens.activities.BaseActivity
import com.alexlyxy.alexdaggertwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexdaggertwo.screens.commonScreens.viewsmvs.ViewMvcFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailsActivity : BaseActivity(), ProductDetailsViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject lateinit var fetchProductDetailsUseCase: FetchProductDetailsUseCase
    @Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var screensNavigator: ScreensNavigator
    @Inject lateinit var viewMvcFactory: ViewMvcFactory

    private lateinit var viewMvc: ProductDetailsViewMvc

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = viewMvcFactory.newProductDetailsViewMvc(null)
        setContentView(viewMvc.rootView)

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


