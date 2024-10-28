package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity

import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app.AppComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.PresentationModule
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.presentation.UseCasesModule
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(
        presentationModule: PresentationModule,
        useCasesModule: UseCasesModule
    ): PresentationComponent


}