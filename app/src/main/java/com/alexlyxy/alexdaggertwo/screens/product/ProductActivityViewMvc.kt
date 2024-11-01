package com.alexlyxy.alexdaggertwo.screens.product

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexdaggertwo.R
import com.alexlyxy.alexdaggertwo.databinding.ProductItemBinding
import com.alexlyxy.alexdaggertwo.products.Product
import com.alexlyxy.alexdaggertwo.products.ProductModel
import com.alexlyxy.alexdaggertwo.screens.commonScreens.viewsmvs.BaseViewMvc
import com.squareup.picasso.Picasso

class ProductActivityViewMvc(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) : BaseViewMvc<ProductActivityViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.activity_product
) {

    interface Listener {
        fun onRefreshClicked()
        fun onProductClicked(clickedProduct: Product)
    }

    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val productAdapter: ProductAdapter

    init {
        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        productAdapter = ProductAdapter { clickedProduct ->
            for (listener in listeners) {
                listener.onProductClicked(clickedProduct)
            }
        }
        recyclerView.adapter = productAdapter
    }

    fun bindProduct(products: List<Product>) {
        productAdapter.bindData(products)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    class ProductAdapter(
        private val onProductClickListener: (Product) -> Unit
    ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        private var productList: List<Product> = ArrayList(0)

        inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = ProductItemBinding.bind(view)
            private var itemProduct: ProductModel? = null

            fun bind(item: ProductModel) = with((binding)) {
                itemProduct = item
                tvTitleView.text = buildString {
                    append(" TITLE : ")
                    append(item.titleView)
                }
                tvDescriptionView.text = buildString {
                    append("      DESCRIPTION : ")
                    append(item.descriptionView)
                }
                Picasso.get().load(productList[position].images[0]).into(ivImageOneProduct)
            }
        }

        //@SuppressLint("NotifyDataSetChanged")
        fun bindData(products: List<Product>) {
            productList = ArrayList(products)
            notifyDataSetChanged()
            Log.d("MyLog", "productListProductActivity : $productList")
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

            holder.bind(
                item = ProductModel(
                    titleView = productList[position].title!!,
                    descriptionView = productList[position].description!!,
                    imageOneProduct = productList[position].images[0]
                )
            )
            holder.itemView.setOnClickListener {
                onProductClickListener.invoke(productList[position])
            }
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }
}