package com.ladsoft.bakingapp.service.di;

import com.ladsoft.bakingapp.service.IngredientUpdateService;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class IngredientUpdateServiceModule {
    @Binds
    abstract IngredientUpdateService providesService(IngredientUpdateService service);
}
