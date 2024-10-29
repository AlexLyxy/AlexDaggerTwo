package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity

import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent( modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(): PresentationComponent


}