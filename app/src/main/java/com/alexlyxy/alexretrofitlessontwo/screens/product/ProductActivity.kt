package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

class ProductActivity : BaseActivity()  {
//    , ProductActivityViewMvc.Listener {

//    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
//
//    private var isDataLoaded = false
//
//    private lateinit var viewMvc: ProductActivityViewMvc
//
//    private lateinit var fetchProductUseCase: FetchProductUseCase
//
//    private lateinit var dialogsNavigator: DialogsNavigator
//
//    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // viewMvc = ProductActivityViewMvc(LayoutInflater.from(this), null)
        //Parent = null for Activityy, but for Fragment not

        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_content, ProductFragment())
                .commit()
        }

//        fetchProductUseCase = compositionRoot.fetchProductUseCase
//
//        dialogsNavigator = compositionRoot.dialogsNavigator
//
//        screensNavigator = compositionRoot.screensNavigator
    }

//    override fun onStart() {
//        super.onStart()
//        viewMvc.registerListener(this)
//        if (!isDataLoaded) {
//            fetchProduct()
//            Toast.makeText(applicationContext, "ActivityProductOnStart", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        coroutineScope.coroutineContext.cancelChildren()
//        viewMvc.unregisterListener(this)
//    }
//
//    override fun onRefreshClicked() {
//        fetchProduct()
//    }
//
//    private fun fetchProduct() {
//        coroutineScope.launch {
//            viewMvc.showProgressIndication()
//            try {
//                val result = fetchProductUseCase.fetchLatestProduct()
//                when (result) {
//                    is FetchProductUseCase.Result.Success -> {
//                        viewMvc.bindProduct(result.products)
//                        isDataLoaded = true
//                    }
//
//                    is FetchProductUseCase.Result.Failure -> onFetchFailed()
//                }
//
//            } finally {
//                viewMvc.hideProgressIndication()
//            }
//        }
//    }
//
//    private fun onFetchFailed() {
//        dialogsNavigator.showServerErrorDialog()
//    }
//
//    override fun onProductClicked(clickedProduct: Product) {
//        clickedProduct.id?.let { screensNavigator.toQuestionDetails(it) }
//    }
}


















