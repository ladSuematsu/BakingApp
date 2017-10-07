package com.ladsoft.bakingapp.mvp.view

import com.ladsoft.bakingapp.entity.Recipe


interface RecipeView {
    fun showRefresh(show : Boolean)
    fun onRecipesLoaded(recipes: List<Recipe>)
    fun onRecipeLoadError()
}