package com.ladsoft.bakingapp.fragment.di

import com.ladsoft.bakingapp.fragment.RecipesFragment
import com.ladsoft.bakingapp.mvp.RecipesMvp
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter
import dagger.Module
import dagger.Provides

@Module
class RecipesFragmentModule {
    @Provides
    fun providesView(view: RecipesFragment): RecipesFragment = view

    @Provides
    fun providesPresenter(model: RecipesMvp.Model): RecipesPresenter = RecipesPresenter(model)
}