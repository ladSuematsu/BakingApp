package com.ladsoft.bakingapp.activity.di;

import com.ladsoft.bakingapp.activity.RecipeActivity;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RecipeActivityModule {
    @Binds
    abstract RecipeMvp.View providesView(RecipeActivity view);

    @Provides
    static RecipePresenter providesPresenter(RecipeMvp.Model model){ return new RecipePresenter(model); }

    @Provides
    static StepPresenter providesStepPresenter() { return new StepPresenter(); }
}