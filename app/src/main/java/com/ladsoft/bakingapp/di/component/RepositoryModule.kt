package com.ladsoft.bakingapp.di.component

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun providesRecipeRepository(): RecipeRepository = RecipeRepository()

    @Provides @Singleton
    fun providesLocalRecipeRepository(appDatabase: AppDatabase): DatabaseRecipeRepository = DatabaseRecipeRepository(appDatabase)
}