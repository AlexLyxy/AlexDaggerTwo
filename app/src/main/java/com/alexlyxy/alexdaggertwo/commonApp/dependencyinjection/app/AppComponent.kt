package com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.app

import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.activity.ActivityComponent
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.service.ServiceComponent
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponentBuilder(): ActivityComponent.Builder

    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent

}