package com.ladsoft.bakingapp.di.component;

import android.app.Application;

import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.di.module.ActivityBuilder;
import com.ladsoft.bakingapp.di.module.ApiModule;
import com.ladsoft.bakingapp.di.module.AppModule;
import com.ladsoft.bakingapp.di.module.ModelModule;
import com.ladsoft.bakingapp.di.module.RepositoryModule;
import com.ladsoft.bakingapp.di.module.ServiceBuilder;
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider;
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ApiModule.class,
                RepositoryModule.class,
                ModelModule.class,
                ActivityBuilder.class,
                ServiceBuilder.class
        })
public interface AppComponent {

    void inject(BakingAppApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }

    void inject(IngredientsListWidgetService.ListRemoteViewsFactory viewFactory);
    void inject(BakingAppAppWidgetProvider provider);
}