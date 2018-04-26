package com.ladsoft.bakingapp.mvp

import com.ladsoft.bakingapp.entity.Recipe


interface RecipesMvp {
    interface View {
        fun showRefresh(show : Boolean)
        fun onRecipesLoaded(recipes: MutableList<Recipe>?)
        fun onRecipeLoadError()
    }

    interface Model {
        fun setListener(listener: Callback)
        fun detachRecipesModelListener()
        fun loadRecipes()

        interface Callback {
            fun onDataLoaded(recipes: MutableList<Recipe>)
            fun onDataLoadError(recipes: MutableList<Recipe>)
            fun onDataLoadError()
        }
    }
}