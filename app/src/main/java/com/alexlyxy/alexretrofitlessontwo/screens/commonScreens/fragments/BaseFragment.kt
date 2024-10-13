package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.fragments

import androidx.fragment.app.Fragment
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.PresentationModule
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

open class BaseFragment : Fragment() {

    protected val compositionRoot by lazy {
        PresentationModule(
            (requireActivity() as BaseActivity)
                .activityCompositionRoot)
    }
}