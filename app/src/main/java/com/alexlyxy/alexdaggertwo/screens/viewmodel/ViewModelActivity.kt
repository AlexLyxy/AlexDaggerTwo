package com.alexlyxy.alexdaggertwo.screens.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexlyxy.alexdaggertwo.R
import com.alexlyxy.alexdaggertwo.screens.commonScreens.ScreensNavigator
import com.alexlyxy.alexdaggertwo.screens.commonScreens.activities.BaseActivity
import com.alexlyxy.alexdaggertwo.screens.commonScreens.toolbar.MyToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewModelActivity: BaseActivity() {

    @Inject lateinit var screensNavigator: ScreensNavigator

    private val myViewModel: MyViewModel by viewModels()
    private  val myViewModel2: MyViewModel2 by viewModels()

    private lateinit var toolbar: MyToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_view_model)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            screensNavigator.navigateBack()
        }

//        myViewModel = ViewModelProvider(this)
//            .get(MyViewModel::class.java)
//        myViewModel2 = ViewModelProvider(this)
//            .get(MyViewModel2::class.java)

        myViewModel.products.observe(this, Observer {
                products -> Toast.makeText(this, "fetched ${products.size} products", Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewModelActivity::class.java)
            context.startActivity(intent)
        }
    }
}