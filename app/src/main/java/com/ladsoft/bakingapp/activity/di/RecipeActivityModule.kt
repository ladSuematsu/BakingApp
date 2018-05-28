package com.ladsoft.bakingapp.activity.di

import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.mvp.RecipeMvp
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter
import dagger.Module
import dagger.Provides

@Module
class RecipeActivityModule {
    @Provides
    fun providesView(view: RecipeActivity): RecipeMvp.View = view

    @Provides
    fun providesPresenter(model: RecipeMvp.Model): RecipePresenter = RecipePresenter(model)
}