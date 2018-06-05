package com.ladsoft.bakingapp.mvp;

import com.ladsoft.bakingapp.entity.Recipe;

import java.util.List;

public interface RecipesMvp {
    interface View {
        void showRefresh(boolean show);
        void onRecipesLoaded(List<Recipe> recipes);
        void onRecipeLoadError();
    }

    interface Model {
        void setListener(Callback listener);
        void detachRecipesModelListener();
        void loadRecipes();

        interface Callback {
            void onDataLoaded(List<Recipe> recipes);
            void onDataLoadError(List<Recipe> recipes);
            void onDataLoadError();
        }
    }
}