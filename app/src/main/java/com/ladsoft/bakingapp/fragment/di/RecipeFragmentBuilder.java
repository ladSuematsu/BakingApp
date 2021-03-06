package com.ladsoft.bakingapp.fragment.di;

import com.ladsoft.bakingapp.fragment.RecipeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeFragmentBuilder {
    @ContributesAndroidInjector(modules = RecipeFragmentModule.class)
    abstract RecipeFragment bindFragment();
}