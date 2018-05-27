package com.ladsoft.bakingapp.di.component

import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.activity.RecipeStepActivity
import com.ladsoft.bakingapp.activity.di.RecipeStepActiviyModule
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.di.module.AppModule
import com.ladsoft.bakingapp.di.module.ModelModule
import com.ladsoft.bakingapp.di.module.RepositoryModule
import com.ladsoft.bakingapp.fragment.RecipesFragment
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState
import com.ladsoft.bakingapp.service.IngredientUpdateService
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, ModelModule::class
, RecipeStepActiviyModule::class
])
interface AppComponent {

    fun inject(application: BakingAppApplication)

    fun inject(activity: RecipeActivity)
    fun inject(fragment: RecipesFragment)

    fun inject(recipeState: RecipeState)
    fun inject(viewFactory: IngredientsListWidgetService.ListRemoteViewsFactory)
    fun inject(activity: RecipeStepActivity)
    fun inject(provider: BakingAppAppWidgetProvider)
    fun inject(service: IngredientUpdateService)

    object Initializer {
        fun initialize(application: BakingAppApplication): AppComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(application))
                .build()

    }
}