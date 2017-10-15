package com.ladsoft.bakingapp.mvp

import com.ladsoft.bakingapp.entity.Recipe

interface RecipeMvp {
    interface View {
        fun showRefresh(show : Boolean)
        fun onRecipeLoaded(recipe: Recipe)
        fun onRecipeLoadError()
    }

    interface Model {
        fun setListener(listener: Callback)
        fun detachListener()
        fun loadRecipe(recipeId: Long)

        interface Callback {
            fun onDataLoaded(recipe: Recipe)
            fun onDataLoadError()
        }
    }

    interface StateContainer {
        val EXTRA_RECIPE_ID: String get() = "extra_recipe_id"

        var recipeId : Long

        fun getStateMap() : HashMap<String, Any>
    }
}