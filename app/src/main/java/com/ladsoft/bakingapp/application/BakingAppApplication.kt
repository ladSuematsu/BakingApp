package com.ladsoft.bakingapp.application

import android.app.Application
import android.content.Context
import com.ladsoft.bakingapp.di.component.AppComponent


class BakingAppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = AppComponent.Initializer.initialize(this)
    }

    companion object {
        fun get(context : Context) : BakingAppApplication = context.getApplicationContext() as BakingAppApplication
        lateinit var appComponent : AppComponent
    }
}