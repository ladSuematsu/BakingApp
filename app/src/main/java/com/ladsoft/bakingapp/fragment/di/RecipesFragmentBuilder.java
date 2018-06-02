package com.ladsoft.bakingapp.fragment.di;

import com.ladsoft.bakingapp.fragment.RecipesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipesFragmentBuilder {
    @ContributesAndroidInjector(modules = RecipesFragmentModule.class)
    abstract RecipesFragment bindFragment();
}