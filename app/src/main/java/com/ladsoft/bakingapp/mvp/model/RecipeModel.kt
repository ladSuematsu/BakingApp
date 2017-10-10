package com.ladsoft.bakingapp.mvp.model

import android.os.Handler
import android.util.Log
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.RecipeMvp
import javax.inject.Inject


class RecipeModel : RecipeMvp.Model {
    val LOG_TAG = RecipeModel::class.java.simpleName

    var modelListener: RecipeMvp.Model.Callback? = null
    val mainHandler = Handler()
    val taskHandler = TaskHandler()
    @Inject lateinit var cacheRecipesRepository: DatabaseRecipeRepository
    @Inject lateinit var cacheIngredientsRepository: DatabaseIngredientRepository
    @Inject lateinit var cacheStepsRepository: DatabaseStepRepository

    init {
        taskHandler.start()
        taskHandler.prepareWorkerHandler()
        BakingAppApplication.appComponent.inject(this)
    }

    override fun setListener(listener: RecipeMvp.Model.Callback) {
        modelListener = listener
    }

    override fun detachListener() {
        modelListener = null
    }

    override fun loadRecipe(recipeId: Long) {
        taskHandler.postTask(Runnable {
            try {
                val recipe: Recipe? = cacheRecipesRepository.loadRecipe(recipeId)
                recipe?.ingredients = cacheIngredientsRepository.loadForRecipeId(recipeId)
                recipe?.steps = cacheStepsRepository.loadForRecipeId(recipeId)

                mainHandler.post({
                    when (recipe) {
                        null -> modelListener?.onDataLoadError()
                        else -> modelListener?.onDataLoaded(recipe)
                    }
                })
            } catch (e: Exception){
                Log.e(LOG_TAG, "Something went wrong", e)
                modelListener?.onDataLoadError()
            }
        })
    }

}