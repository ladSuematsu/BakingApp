package com.ladsoft.bakingapp.di.module

import android.content.Context
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.mvp.RecipeMvp
import com.ladsoft.bakingapp.mvp.RecipesMvp
import com.ladsoft.bakingapp.mvp.model.EspressoRecipeModel
import com.ladsoft.bakingapp.mvp.model.RecipeModel
import com.ladsoft.bakingapp.mvp.model.RecipesModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MvpModelModule {

    @Provides @Singleton
    fun providesRecipesModel(recipesRepository: RecipeRepository,
                         cacheRecipesRepository: DatabaseRecipeRepository,
                         cacheIngredientRepository: DatabaseIngredientRepository,
                         cacheStepRepository: DatabaseStepRepository):
            RecipesMvp.Model = EspressoRecipeModel(recipesRepository,
                                        cacheRecipesRepository,
                                        cacheIngredientRepository,
                                        cacheStepRepository)

    @Provides @Singleton
    fun providesRecipeModel(cacheRecipesRepository: DatabaseRecipeRepository,
                            cacheIngredientRepository: DatabaseIngredientRepository,
                            cacheStepRepository: DatabaseStepRepository):
            RecipeMvp.Model = RecipeModel(cacheRecipesRepository,
            cacheIngredientRepository,
            cacheStepRepository)
}