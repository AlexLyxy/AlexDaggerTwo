package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.alexlyxy.alexretrofitlessontwo.networking.ProductApi
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.ScreensNavigator
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun activity(): AppCompatActivity

    fun layoutInflater(): LayoutInflater

    fun fragmentManager(): FragmentManager

    fun productApi(): ProductApi

    fun screensNavigator(): ScreensNavigator

}