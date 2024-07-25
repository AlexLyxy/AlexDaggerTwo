package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alexlyxy.alexretrofitlessontwo.MyApplication
import com.alexlyxy.alexretrofitlessontwo.commonApp.composition.ActivityCompositionRoot
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

open class BaseFragment : Fragment(){

    protected val compositionRoot get() = (requireActivity() as BaseActivity).compositionRoot

}