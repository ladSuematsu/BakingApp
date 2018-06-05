package com.ladsoft.bakingapp.di.module;

import com.ladsoft.bakingapp.data.database.AppDatabase;
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.data.network.api.BakingAppApiModule;
import com.ladsoft.bakingapp.data.repository.RecipeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {
    @Provides @Singleton
    static RecipeRepository providesRecipeRepository(BakingAppApiModule apiModule) { return new RecipeRepository(apiModule); }

    @Provides @Singleton
    static DatabaseRecipeRepository providesLocalRecipeRepository(AppDatabase appDatabase) { return new DatabaseRecipeRepository(appDatabase); }

    @Provides @Singleton
    static DatabaseIngredientRepository providesLocalIngredientRepository(AppDatabase appDatabase) { return new DatabaseIngredientRepository(appDatabase); }

    @Provides @Singleton
    static DatabaseStepRepository providesLocalStepRepository(AppDatabase appDatabase) { return new DatabaseStepRepository(appDatabase); }
}