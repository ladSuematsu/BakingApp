package com.ladsoft.bakingapp.fragment.di;

import com.ladsoft.bakingapp.fragment.RecipeFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RecipeFragmentModule {
    @Binds
    abstract RecipeFragment providesView(RecipeFragment view);
}