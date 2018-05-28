package com.ladsoft.bakingapp.di.module

import com.ladsoft.bakingapp.activity.MainActivity
import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.activity.RecipeStepActivity
import com.ladsoft.bakingapp.activity.di.MainActivityModule
import com.ladsoft.bakingapp.activity.di.RecipeActivityModule
import com.ladsoft.bakingapp.activity.di.RecipeStepActivityModule
import com.ladsoft.bakingapp.fragment.di.RecipeFragmentBuilder
import com.ladsoft.bakingapp.fragment.di.RecipesFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class, RecipesFragmentBuilder::class])
    abstract fun providesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [RecipeActivityModule::class, RecipeFragmentBuilder::class])
    abstract fun providesRecipesActivity(): RecipeActivity

    @ContributesAndroidInjector(modules = [RecipeStepActivityModule::class])
    abstract fun providesRecipeStepActivity(): RecipeStepActivity
}