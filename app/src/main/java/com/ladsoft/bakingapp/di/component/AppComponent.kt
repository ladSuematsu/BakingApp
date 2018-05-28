package com.ladsoft.bakingapp.di.component

import android.app.Application
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.di.module.ActivityBuilder
import com.ladsoft.bakingapp.di.module.AppModule
import com.ladsoft.bakingapp.di.module.ModelModule
import com.ladsoft.bakingapp.di.module.RepositoryModule
import com.ladsoft.bakingapp.di.module.ServiceBuilder
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
                AndroidSupportInjectionModule::class,
                AppModule::class,
                RepositoryModule::class,
                ModelModule::class,
                ActivityBuilder::class,
                ServiceBuilder::class
            ])
interface AppComponent {

    fun inject(application: BakingAppApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(viewFactory: IngredientsListWidgetService.ListRemoteViewsFactory)
    fun inject(provider: BakingAppAppWidgetProvider)

//    object Initializer {
//        fun initialize(application: BakingAppApplication): AppComponent = DaggerAppComponent
//                .builder()
//                .application(application)
//                .build()
//                .inject(application)
//        fun initialize(application: BakingAppApplication): AppComponent = DaggerAppComponent
//                .builder()
//                .appModule(AppModule(application))
//                .build()
//    }
}