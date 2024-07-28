package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alexlyxy.alexretrofitlessontwo.products.FetchProductUseCase
import com.alexlyxy.alexretrofitlessontwo.products.Product
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.fragments.BaseFragment
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ProductFragment : BaseFragment(), ProductActivityViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    private lateinit var viewMvc: ProductActivityViewMvc

    private lateinit var fetchProductUseCase: FetchProductUseCase

    private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        //viewMvc = ProductActivityViewMvc(LayoutInflater
        //    .from(requireContext()), container)
        //Parent = null for Activityy, but for Fragment not. Use "container"
        viewMvc = compositionRoot.viewMvcFactory.newProductViewMvc(container)
        return viewMvc.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewMvc = ProductActivityViewMvc(LayoutInflater.from(this), null)
//        //Parent = null for Activityy, but for Fragment not. Use "container"
//        setContentView(viewMvc.rootView)

        fetchProductUseCase = compositionRoot.fetchProductUseCase

        dialogsNavigator = compositionRoot.dialogsNavigator

        screensNavigator = compositionRoot.screensNavigator
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
        clickedProduct.id?.let { screensNavigator.toQuestionDetails(it) }
    }
}


















