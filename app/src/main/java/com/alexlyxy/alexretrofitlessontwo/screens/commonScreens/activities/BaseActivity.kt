package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.MyApplication

open class BaseActivity : AppCompatActivity(){

    val compositionRoot get() = (application as MyApplication).appCompositionRoot

}