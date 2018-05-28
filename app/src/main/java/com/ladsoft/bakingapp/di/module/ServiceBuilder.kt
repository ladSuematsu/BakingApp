package com.ladsoft.bakingapp.di.module

import com.ladsoft.bakingapp.service.IngredientUpdateService
import com.ladsoft.bakingapp.service.di.IngredientUpdateServiceModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {
    @ContributesAndroidInjector(modules = [IngredientUpdateServiceModule::class])
    abstract fun providesIngredientUpdateService(): IngredientUpdateService
}