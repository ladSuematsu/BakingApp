package com.ladsoft.bakingapp.application

import android.app.Application
import com.ladsoft.bakingapp.manager.SessionManager


class BakingAppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        SessionManager.initialize(this)
    }
}