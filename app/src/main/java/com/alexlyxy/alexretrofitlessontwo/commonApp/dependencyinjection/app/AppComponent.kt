package com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.app

import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.ActivityComponent
import com.alexlyxy.alexretrofitlessontwo.commonApp.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent


}