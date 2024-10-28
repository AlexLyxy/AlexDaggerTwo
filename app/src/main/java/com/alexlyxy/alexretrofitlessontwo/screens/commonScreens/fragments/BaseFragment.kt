package com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.fragments

import androidx.fragment.app.Fragment
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.UseCasesModule
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
            .newPresentationComponent(PresentationModule(), UseCasesModule())
    }

    protected val injector get() = presentationComponent
}