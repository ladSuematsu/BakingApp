package com.ladsoft.bakingapp.mvp;

import com.ladsoft.bakingapp.entity.Recipe;

import java.util.HashMap;

public interface RecipeMvp {
    interface View {
        void showRefresh(boolean show);
        void onRecipeLoaded(Recipe recipe);
        void onRecipeLoadError();
    }

    interface Model {
        void setListener(Callback listener);
        void detachListener();
        void loadRecipe(long recipeId);

        interface Callback {
            void onDataLoaded(Recipe recipe);
            void onDataLoadError();
        }
    }

    interface StateContainer {
        String EXTRA_RECIPE_ID = "extra_recipe_id";

        long getRecipeId();

        HashMap<String, Object> getStateMap();
    }
}