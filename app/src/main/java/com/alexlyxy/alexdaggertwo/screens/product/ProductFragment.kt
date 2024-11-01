package com.alexlyxy.alexdaggertwo.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alexlyxy.alexdaggertwo.products.FetchProductUseCase
import com.alexlyxy.alexdaggertwo.products.Product
import com.alexlyxy.alexdaggertwo.screens.commonScreens.ScreensNavigator
import com.alexlyxy.alexdaggertwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexdaggertwo.screens.commonScreens.fragments.BaseFragment
import com.alexlyxy.alexdaggertwo.screens.commonScreens.viewsmvs.ViewMvcFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductFragment : BaseFragment(), ProductActivityViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject  lateinit var fetchProductUseCase: FetchProductUseCase
    @Inject  lateinit var dialogsNavigator: DialogsNavigator
    @Inject  lateinit var screensNavigator: ScreensNavigator
    @Inject  lateinit var viewMvcFactory: ViewMvcFactory

    private lateinit var viewMvc: ProductActivityViewMvc

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc =viewMvcFactory.newProductViewMvc(container)
        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchProduct()
            Toast.makeText(requireContext(), "ActivityProductOnStartFragment", Toast.LENGTH_LONG).show()
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
        clickedProduct.id?.let { screensNavigator.toProductDetails(it) }
    }
}


















