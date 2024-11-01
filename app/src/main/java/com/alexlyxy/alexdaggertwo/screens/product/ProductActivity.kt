package com.alexlyxy.alexdaggertwo.screens.product

import android.os.Bundle
import com.alexlyxy.alexdaggertwo.R
import com.alexlyxy.alexdaggertwo.screens.commonScreens.activities.BaseActivity

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


















