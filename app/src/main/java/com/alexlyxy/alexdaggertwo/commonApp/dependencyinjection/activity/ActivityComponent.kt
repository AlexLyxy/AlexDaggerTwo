package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationComponent
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent( modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule)
    : PresentationComponent

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }

}