package com.ladsoft.bakingapp.service.di

import com.ladsoft.bakingapp.service.IngredientUpdateService
import dagger.Binds
import dagger.Module

@Module
abstract class IngredientUpdateServiceModule {
    @Binds
    abstract fun providesService(service: IngredientUpdateService): IngredientUpdateService
}