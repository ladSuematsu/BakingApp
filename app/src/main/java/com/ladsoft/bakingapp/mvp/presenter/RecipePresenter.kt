package com.ladsoft.bakingapp.mvp.presenter

import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.RecipeMvp

class RecipePresenter(val model: RecipeMvp.Model): Presenter<RecipeMvp.View>(), RecipeMvp.Model.Callback {
    var activityCreation = true;

    init {
        model.setListener(this)
    }

    lateinit var state : RecipeMvp.StateContainer

    fun setData(state : RecipeMvp.StateContainer) {
        this.state = state
    }

    fun loadData() {
        when {
            activityCreation -> {
                view?.showRefresh(true)
                model.loadRecipe(state.recipeId)
            }
        }
    }

    override fun detachView() {
        view?.showRefresh(false)
        super.detachView()
    }

    override fun onDataLoaded(recipe: Recipe) {
        view?.onRecipeLoaded(recipe)
        view?.showRefresh(false)
        activityCreation = false
    }

    override fun onDataLoadError() {
        view?.onRecipeLoadError()
        view?.showRefresh(false)
    }
}