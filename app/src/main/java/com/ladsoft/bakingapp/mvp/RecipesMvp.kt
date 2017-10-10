package com.ladsoft.bakingapp.mvp

import com.ladsoft.bakingapp.entity.Recipe


interface RecipesMvp {
    interface View {
        fun showRefresh(show : Boolean)
        fun onRecipesLoaded(recipes: List<Recipe>)
        fun onRecipeLoadError()
    }

    interface Model {
        fun setListener(listener: Callback)
        fun detachRecipesModelListener()
        fun loadRecipes()

        interface Callback {
            fun onDataLoaded(recipes: List<Recipe>)
            fun onDataLoadError(recipes: List<Recipe>)
            fun onDataLoadError()
        }
    }
}