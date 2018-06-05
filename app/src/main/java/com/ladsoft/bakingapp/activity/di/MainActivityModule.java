package com.ladsoft.bakingapp.activity.di;

import com.ladsoft.bakingapp.activity.MainActivity;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {
    @Binds
    abstract MainActivity providesView(MainActivity view);
}