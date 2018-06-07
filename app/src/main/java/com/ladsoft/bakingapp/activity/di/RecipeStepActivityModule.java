package com.ladsoft.bakingapp.activity.di;

import com.ladsoft.bakingapp.activity.RecipeStepActivity;
import com.ladsoft.bakingapp.mvp.StepsMvp;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RecipeStepActivityModule {
    @Binds
    abstract StepsMvp.View providesView(RecipeStepActivity view);

    @Provides
    static StepPresenter providesPresenter() {
        return new StepPresenter();
    }
}