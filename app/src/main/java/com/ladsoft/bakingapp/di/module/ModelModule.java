package com.ladsoft.bakingapp.di.module;

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.data.repository.RecipeRepository;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.mvp.RecipesMvp;
import com.ladsoft.bakingapp.mvp.model.RecipeModel;
import com.ladsoft.bakingapp.mvp.model.RecipesModel;
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ModelModule {

    @Provides @Singleton
    static RecipesMvp.Model providesRecipesModel(RecipeRepository recipesRepository,
                                                 DatabaseRecipeRepository cacheRecipesRepository,
                                                 DatabaseIngredientRepository cacheIngredientRepository,
                                                 DatabaseStepRepository cacheStepRepository) {
        return new RecipesModel(recipesRepository,
                cacheRecipesRepository,
                cacheIngredientRepository,
                cacheStepRepository);
    }

    @Provides @Singleton
    static RecipeMvp.Model providesRecipeModel(DatabaseRecipeRepository cacheRecipesRepository,
                                               DatabaseIngredientRepository cacheIngredientRepository,
                                               DatabaseStepRepository cacheStepRepository) {
        return new RecipeModel(cacheRecipesRepository,
                cacheIngredientRepository,
                cacheStepRepository);
    }

    @Provides
    static RecipeState providesRecipeState(SessionManager sessionManager) {
        return new RecipeState(sessionManager);
    }
}