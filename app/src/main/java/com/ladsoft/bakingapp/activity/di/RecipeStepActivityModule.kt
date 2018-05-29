package com.ladsoft.bakingapp.activity.di

import com.ladsoft.bakingapp.activity.RecipeStepActivity
import com.ladsoft.bakingapp.mvp.StepsMvp
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter
import dagger.Module
import dagger.Provides

@Module
class RecipeStepActivityModule {
    @Provides
    fun providesView(view: RecipeStepActivity): StepsMvp.View = view

    @Provides
    fun providesPresenter() : StepPresenter = StepPresenter()
}