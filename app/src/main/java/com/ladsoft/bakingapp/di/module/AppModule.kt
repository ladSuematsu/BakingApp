package com.ladsoft.bakingapp.di.module

import android.app.Application
import android.content.res.Resources
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.manager.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: BakingAppApplication) {

    @Provides @Singleton
    fun providesApplication(): Application = application

    @Provides @Singleton
    fun providesResources(): Resources = application.resources

    @Provides @Singleton
    fun providesSessionManager(application: Application): SessionManager = SessionManager(application)

    @Provides @Singleton
    fun providesDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }
}