package com.ladsoft.bakingapp.di.module

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.mvp.RecipeMvp
import com.ladsoft.bakingapp.mvp.RecipesMvp
import com.ladsoft.bakingapp.mvp.model.EspressoRecipeModel
import com.ladsoft.bakingapp.mvp.model.EspressoRecipesModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides @Singleton
    fun providesRecipesModel(recipesRepository: RecipeRepository,
                         cacheRecipesRepository: DatabaseRecipeRepository,
                         cacheIngredientRepository: DatabaseIngredientRepository,
                         cacheStepRepository: DatabaseStepRepository):
            RecipesMvp.Model = EspressoRecipesModel(recipesRepository,
                                        cacheRecipesRepository,
                                        cacheIngredientRepository,
                                        cacheStepRepository)

    @Provides @Singleton
    fun providesRecipeModel(cacheRecipesRepository: DatabaseRecipeRepository,
                            cacheIngredientRepository: DatabaseIngredientRepository,
                            cacheStepRepository: DatabaseStepRepository):
            RecipeMvp.Model = EspressoRecipeModel(cacheRecipesRepository,
            cacheIngredientRepository,
            cacheStepRepository)
}