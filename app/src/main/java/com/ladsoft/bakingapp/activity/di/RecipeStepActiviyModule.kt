package com.ladsoft.bakingapp.activity.di

import com.ladsoft.bakingapp.mvp.presenter.StepPresenter
import dagger.Module
import dagger.Provides

@Module
class RecipeStepActiviyModule {

    @Provides
    fun providesRecipeStepPresenter() : StepPresenter = StepPresenter()
}