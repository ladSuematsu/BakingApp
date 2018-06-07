package com.ladsoft.bakingapp.mvp.presenter;

import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.RecipesMvp;

import java.util.List;

public class RecipesPresenter extends Presenter<RecipesMvp.View> implements RecipesMvp.Model.Callback {

    private final RecipesMvp.Model model;

    public RecipesPresenter(RecipesMvp.Model model) {
        this.model = model;
        this.model.setListener(this);
    }

    public void loadData() {
        if (!isViewAttached()) { return; }

        getView().showRefresh(true);
        model.loadRecipes();
    }

    @Override
    public void detachView() {
        if (isViewAttached()) {
            getView().showRefresh(false);
        }

        super.detachView();
    }

    @Override
    public void onDataLoaded(List<Recipe> recipes) {
        if (!isViewAttached()) { return; }

        getView().onRecipesLoaded(recipes);
        getView().showRefresh(false);
    }

    @Override
    public void onDataLoadError() {
        if (!isViewAttached()) { return; }

        getView().onRecipeLoadError();
        getView().showRefresh(false);
    }

    @Override
    public void onDataLoadError(List<Recipe> recipes) {
        if (!isViewAttached()) { return; }

        getView().onRecipeLoadError();
        getView().onRecipesLoaded(recipes);
        getView().showRefresh(false);
    }
}
