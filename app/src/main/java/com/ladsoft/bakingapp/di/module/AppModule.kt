package com.ladsoft.bakingapp.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.manager.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
//class AppModule(private var application: BakingAppApplication) {
class AppModule {

    @Provides @Singleton
    fun providesContext(application: Application): Context = application.applicationContext

    @Provides @Singleton
    fun providesResources(context: Context): Resources = context.resources

    @Provides @Singleton
    fun providesSessionManager(context: Context): SessionManager = SessionManager(context)

    @Provides @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}