package com.ladsoft.bakingapp.mvp.presenter

import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.model.RecipesModel
import com.ladsoft.bakingapp.mvp.view.RecipeView

class RecipesPresenter(val model: RecipesModel): Presenter<RecipeView>(), RecipesModel.RecipesModelListener {
    init {
        model.setListener(this)
    }

    fun loadData() {
        model.loadRecipes()
    }

    override fun onDataLoaded(recipes: List<Recipe>) {
        view?.onRecipesLoaded(recipes)
    }

    override fun onDataLoadError() {
        view?.onRecipeLoadError()
    }
}

