package com.alexlyxy.alexdaggertwo.commonApp.service

import android.app.Service
import com.alexlyxy.alexdaggertwo.MyApplication
import com.alexlyxy.alexdaggertwo.commonApp.dependencyinjection.service.ServiceModule

abstract class BaseService: Service () {

    private val appComponent get() = (application as MyApplication).appComponent

    val serviceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }

}