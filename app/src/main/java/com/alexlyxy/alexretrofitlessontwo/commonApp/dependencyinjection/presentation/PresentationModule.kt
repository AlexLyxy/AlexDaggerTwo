package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.dialogs.DialogsNavigator
import com.alexlyxy.alexretrofitlessontwo.screens.commonScreens.viewsmvs.ViewMvcFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule  {

    @Provides
    fun viewMvcFactory (layoutInflater: LayoutInflater) = ViewMvcFactory(layoutInflater)

    @Provides
   fun dialogsNavigator (fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

}