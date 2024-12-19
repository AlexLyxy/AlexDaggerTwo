package com.alexlyxy.alexdaggertwo.screens.commonScreens.dialogs

import androidx.fragment.app.DialogFragment
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationModule
import com.alexlyxy.alexdaggertwo.screens.commonScreens.activities.BaseActivity

class BaseDialog: DialogFragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }

    protected val injector get() = presentationComponent

}