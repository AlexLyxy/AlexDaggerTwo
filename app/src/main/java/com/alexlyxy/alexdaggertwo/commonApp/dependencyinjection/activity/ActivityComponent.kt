package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.presentation.PresentationComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent( modules = [ActivityModule::class])
interface ActivityComponent {

   //fun newPresentationComponent(): PresentationComponent

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }

}