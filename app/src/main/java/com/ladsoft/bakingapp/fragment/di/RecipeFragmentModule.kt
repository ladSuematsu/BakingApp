package com.ladsoft.bakingapp.fragment.di

import com.ladsoft.bakingapp.fragment.RecipeFragment
import dagger.Module
import dagger.Provides

@Module
class RecipeFragmentModule {
    @Provides
    fun providesView(view: RecipeFragment): RecipeFragment = view
}