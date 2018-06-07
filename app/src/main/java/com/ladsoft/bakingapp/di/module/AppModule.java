package com.ladsoft.bakingapp.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.ladsoft.bakingapp.data.database.AppDatabase;
import com.ladsoft.bakingapp.manager.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides @Singleton
    static Context providesContext(Application application) { return application.getApplicationContext(); }

    @Provides @Singleton
    static Resources providesResources(Context context) { return context.getResources(); }

    @Provides @Singleton
    static SessionManager providesSessionManager(Context context) { return new SessionManager(context); }

    @Provides @Singleton
    static AppDatabase providesDatabase(Context context) {
        return AppDatabase.getDatabase(context);
    }
}