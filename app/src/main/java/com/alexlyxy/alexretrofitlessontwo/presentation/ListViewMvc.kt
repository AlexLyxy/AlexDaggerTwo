package com.alexlyxy.alexretrofitlessontwo.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.presentation.common.viewsmvc.BaseViewMvc

class ListViewMvc(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) : BaseViewMvc<ListViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.activity_main
) {

    interface Listener {
        fun onRefreshClicked()
    }

    private val swipeRefresh: SwipeRefreshLayout

    init {
        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
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
