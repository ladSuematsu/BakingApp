package com.ladsoft.bakingapp.di.component

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
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

    @Provides @Singleton
    fun providesLocalIngredientRepository(appDatabase: AppDatabase): DatabaseIngredientRepository = DatabaseIngredientRepository(appDatabase)

    @Provides @Singleton
    fun providesLocalStepRepository(appDatabase: AppDatabase): DatabaseStepRepository= DatabaseStepRepository(appDatabase)
}