package com.alexlyxy.alexdaggertwo.screens.commonScreens.fragments

import androidx.fragment.app.Fragment
import com.alexlyxy.alexdaggertwo.screens.commonScreens.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
            .newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}