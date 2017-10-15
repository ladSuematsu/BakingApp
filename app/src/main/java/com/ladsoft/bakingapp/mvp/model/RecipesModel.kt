package com.ladsoft.bakingapp.mvp.model

import android.os.Handler
import android.util.Log
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.mvp.RecipesMvp
import javax.inject.Inject


class RecipesModel: RecipesMvp.Model {
    val LOG_TAG = RecipesModel::class.java.simpleName
    var recipesModelListener: RecipesMvp.Model.Callback? = null
    val handler = Handler()
    val taskHandler = TaskHandler()
    @Inject lateinit var recipesRepository: RecipeRepository
    @Inject lateinit var cacheRecipesRepository: DatabaseRecipeRepository
    @Inject lateinit var cacheIngredientRepository: DatabaseIngredientRepository
    @Inject lateinit var cacheStepRepository: DatabaseStepRepository

    init {
        taskHandler.start()
        taskHandler.prepareWorkerHandler()
        BakingAppApplication.appComponent.inject(this)
    }

    override fun setListener(listener: RecipesMvp.Model.Callback) {
        recipesModelListener = listener
    }

    override fun detachRecipesModelListener() {
        recipesModelListener = null
    }

    override fun loadRecipes() {
        taskHandler.postTask(Runnable {
            try {
                val recipes = recipesRepository.recipes()

                val resultIds = HashSet<Long>(recipes.map {it.id})
                if (recipes.isNotEmpty()) {
                    cacheRecipesRepository.deletePreserving(resultIds.toList())
                }

                // Save into local database
                recipes.forEach {
                    try {
                        cacheRecipesRepository.insert(it)
                        cacheIngredientRepository.insert(it.ingredients)
                        cacheStepRepository.insert(it.steps)
                    } catch (e: Exception) {
                        Log.e(LOG_TAG, "loadRecipes(): Something went while processing the recipes", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "loadRecipes(): Something went wrong", e)
            } finally {
                try {
                    // Always load from local database
                    val recipes = cacheRecipesRepository.loadRecipes()
                    handler.post({
                        recipesModelListener?.onDataLoaded(recipes)
                    })
                } catch (ex: Exception){
                    recipesModelListener?.onDataLoadError()
                }
            }
        })
    }
}