package com.ladsoft.bakingapp.mvp.model

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
import com.ladsoft.bakingapp.mvp.RecipesMvp


class EspressoRecipeModel (recipesRepository: RecipeRepository,
                           cacheRecipesRepository: DatabaseRecipeRepository,
                           cacheIngredientRepository: DatabaseIngredientRepository,
                           cacheStepRepository: DatabaseStepRepository): RecipesMvp.Model, RecipesMvp.Model.Callback {
    private val model: RecipesMvp.Model = RecipesModel(recipesRepository,
        cacheRecipesRepository,
        cacheIngredientRepository,
        cacheStepRepository)
    private var listener: RecipesMvp.Model.Callback? = null

    override fun setListener(listener: RecipesMvp.Model.Callback) {
        this.listener = listener
        model.setListener(this)
    }

    override fun detachRecipesModelListener() {
        this.listener = null
        model.detachRecipesModelListener()
    }

    override fun loadRecipes() {
        EspressoIdlingResource.increment()
        model.loadRecipes()
    }

    override fun onDataLoaded(recipes: List<Recipe>) {
        EspressoIdlingResource.decrement()
        listener?.onDataLoaded(recipes)
    }

    override fun onDataLoadError(recipes: List<Recipe>) {
        EspressoIdlingResource.decrement()
        listener?.onDataLoadError(recipes)
    }

    override fun onDataLoadError() {
        EspressoIdlingResource.decrement()
        listener?.onDataLoadError()
    }


}
