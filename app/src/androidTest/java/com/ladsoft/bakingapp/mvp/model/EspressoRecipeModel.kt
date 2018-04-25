package com.ladsoft.bakingapp.mvp.model

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
import com.ladsoft.bakingapp.mvp.RecipeMvp
import com.ladsoft.bakingapp.mvp.RecipesMvp


class EspressoRecipeModel(cacheRecipesRepository: DatabaseRecipeRepository,
                          cacheIngredientRepository: DatabaseIngredientRepository,
                          cacheStepRepository: DatabaseStepRepository): RecipeMvp.Model, RecipeMvp.Model.Callback {
    private val model: RecipeMvp.Model = RecipeModel(cacheRecipesRepository,
        cacheIngredientRepository,
        cacheStepRepository)
    private var listener: RecipeMvp.Model.Callback? = null

    override fun setListener(listener: RecipeMvp.Model.Callback) {
        this.listener = listener
        model.setListener(this)
    }

    override fun detachListener() {
        this.listener = null
        model.detachListener()
    }

    override fun loadRecipe(recipeId: Long) {
        EspressoIdlingResource.increment()
        model.loadRecipe(recipeId)
    }

    override fun onDataLoaded(recipe: Recipe) {
        EspressoIdlingResource.decrement()
        listener?.onDataLoaded(recipe)
    }

    override fun onDataLoadError() {
        EspressoIdlingResource.decrement()
        listener?.onDataLoadError()
    }

}
