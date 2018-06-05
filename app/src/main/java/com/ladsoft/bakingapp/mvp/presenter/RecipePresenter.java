package com.ladsoft.bakingapp.mvp.presenter;

import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

public class RecipePresenter extends Presenter<RecipeMvp.View> implements RecipeMvp.Model.Callback {
    private final RecipeMvp.Model model;

    private boolean activityCreation = true;
    private RecipeMvp.StateContainer state;

    public RecipePresenter(RecipeMvp.Model model) {
        this.model = model;
        model.setListener(this);
    }

    public void setData(RecipeMvp.StateContainer state) {
        this.state = state;
    }

    public RecipeMvp.StateContainer getData() {
        return this.state;
    }

    public void loadData() {
        if (!isViewAttached()) { return; }

        if (activityCreation) {
            getView().showRefresh(true);
            model.loadRecipe(state.getRecipeId());
        }
    }

    @Override
    public void detachView() {
        getView().showRefresh(false);
        super.detachView();
    }

    @Override
    public void onDataLoaded(Recipe recipe) {
        if (!isViewAttached()) { return; }

        getView().onRecipeLoaded(recipe);
        getView().showRefresh(false);
        activityCreation = false;
    }

    @Override
    public void onDataLoadError() {
        if (!isViewAttached()) { return; }

        getView().onRecipeLoadError();
        getView().showRefresh(false);
    }
}
