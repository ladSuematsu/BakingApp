package com.ladsoft.bakingapp.mvp.presenter

import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.model.RecipesModel
import com.ladsoft.bakingapp.mvp.view.RecipeView

class RecipesPresenter(val model: RecipesModel): Presenter<RecipeView>(), RecipesModel.RecipesModelListener {
    init {
        model.setListener(this)
    }

    fun loadData() {
        view?.showRefresh(true)
        model.loadRecipes()
    }

    override fun detachView() {
        view?.showRefresh(false)
        super.detachView()
    }

    override fun onDataLoaded(recipes: List<Recipe>) {
        view?.onRecipesLoaded(recipes)
        view?.showRefresh(false)
    }

    override fun onDataLoadError() {
        view?.onRecipeLoadError()
        view?.showRefresh(false)
    }

    override fun onDataLoadError(recipes: List<Recipe>) {
        view?.onRecipeLoadError()
        view?.onRecipesLoaded(recipes)
        view?.showRefresh(false)
    }
}

