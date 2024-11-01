package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity

import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent( modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(): PresentationComponent


}