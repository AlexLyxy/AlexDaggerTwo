package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.fragments

import androidx.fragment.app.Fragment
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.Injector
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.DaggerPresentationComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationModule
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule((requireActivity() as BaseActivity).activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}