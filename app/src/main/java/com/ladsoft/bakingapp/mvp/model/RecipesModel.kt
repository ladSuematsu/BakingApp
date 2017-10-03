package com.ladsoft.bakingapp.mvp.model

import android.os.Handler
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.data.repository.RecipeRepository
import com.ladsoft.bakingapp.entity.Recipe
import javax.inject.Inject


class RecipesModel  {
    var recipesModelListener: RecipesModelListener? = null
    val handler = Handler()
    val taskHandler = TaskHandler()
    @Inject lateinit var recipesRepository: RecipeRepository
    @Inject lateinit var cacheRecipesRepository: DatabaseRecipeRepository

    init {
        taskHandler.start()
        taskHandler.prepareWorkerHandler()
        BakingAppApplication.appComponent.inject(this)
    }


    fun setListener(listener: RecipesModelListener) {
        recipesModelListener = listener
    }

    fun detachRecipesModelListener() {
        recipesModelListener = null
    }

    fun loadRecipes() {
        taskHandler.postTask(Runnable {
            val recipes: List<Recipe>? = recipesRepository.recipes()

            handler.post( {
                if (recipes == null || recipes.isEmpty()) {
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