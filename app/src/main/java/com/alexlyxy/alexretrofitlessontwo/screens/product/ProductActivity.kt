package com.alexlyxy.alexretrofitlessontwo.screens.product

import android.os.Bundle
import com.alexlyxy.alexretrofitlessontwo.R
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

class ProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_content, ProductFragment())
                .commit()
        }
    }
}


















