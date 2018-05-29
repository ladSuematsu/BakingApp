package com.ladsoft.bakingapp.application

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import com.ladsoft.bakingapp.di.component.AppComponent
import com.ladsoft.bakingapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject


class BakingAppApplication: Application(), HasActivityInjector, HasServiceInjector {
    @Inject lateinit var dispatchingActivityAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var dispatchingServiceAndroidInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceAndroidInjector
    }

    companion object {
        fun get(context : Context) : BakingAppApplication = context.getApplicationContext() as BakingAppApplication
        lateinit var appComponent : AppComponent
    }
}