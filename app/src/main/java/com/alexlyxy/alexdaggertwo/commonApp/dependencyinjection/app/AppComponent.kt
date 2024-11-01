package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.app

import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity.ActivityComponent
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent


}