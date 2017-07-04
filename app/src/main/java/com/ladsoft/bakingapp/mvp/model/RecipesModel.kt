package com.ladsoft.bakingapp.mvp.model

import android.os.Handler
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.entity.Recipe


class RecipesModel  {
    var recipesModelListener: RecipesModelListener? = null
    val handler = Handler()
    val taskHandler = TaskHandler()

    init {
        taskHandler.start()
        taskHandler.prepareWorkerHandler()
    }

    fun setListener(listener: RecipesModelListener) {
        recipesModelListener = listener
    }

    fun detachRecipesModelListener() {
        recipesModelListener = null
    }

    fun loadRecipes() {
        taskHandler.postTask(Runnable {
            val recipes = RecipeRepository.Companion.instance.recipes

            handler.post( {
                if (recipes.isEmpty()) {
                    recipesModelListener?.onDataLoadError()
                } else {
                    recipesModelListener?.onDataLoaded(recipes)
                }
            } )
        })
    }

    interface RecipesModelListener {
        fun onDataLoaded(recipes: List<Recipe>)
        fun onDataLoadError()
    }
}