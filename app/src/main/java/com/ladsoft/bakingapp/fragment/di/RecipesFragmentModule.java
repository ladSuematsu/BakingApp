package com.ladsoft.bakingapp.fragment.di;

import com.ladsoft.bakingapp.fragment.RecipesFragment;
import com.ladsoft.bakingapp.mvp.RecipesMvp;
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RecipesFragmentModule {
    @Binds
    abstract RecipesFragment providesView(RecipesFragment view);

    @Provides
    static RecipesPresenter providesPresenter(RecipesMvp.Model model) {
        return new RecipesPresenter(model);
    }
}