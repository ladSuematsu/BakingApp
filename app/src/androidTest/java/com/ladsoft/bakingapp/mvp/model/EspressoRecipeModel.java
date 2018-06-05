package com.ladsoft.bakingapp.mvp.model;

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource;
import com.ladsoft.bakingapp.mvp.RecipeMvp;


public class EspressoRecipeModel implements RecipeMvp.Model, RecipeMvp.Model.Callback {
    private final RecipeMvp.Model model;
    private RecipeMvp.Model.Callback listener;

    public EspressoRecipeModel(DatabaseRecipeRepository cacheRecipesRepository,
                               DatabaseIngredientRepository cacheIngredientRepository,
                               DatabaseStepRepository cacheStepRepository) {
        this.model = new RecipeModel(cacheRecipesRepository,
                                        cacheIngredientRepository,
                                        cacheStepRepository);
    }


    @Override
    public void setListener(RecipeMvp.Model.Callback listener) {
        this.listener = listener;
        model.setListener(this);
    }

    @Override
    public void detachListener() {
        this.listener = null;
        model.detachListener();
    }

    @Override
    public void loadRecipe(long recipeId) {
        EspressoIdlingResource.increment();
        model.loadRecipe(recipeId);
    }

    @Override
    public void onDataLoaded(Recipe recipe) {
        EspressoIdlingResource.decrement();
        listener.onDataLoaded(recipe);
    }

    @Override
    public void  onDataLoadError() {
        EspressoIdlingResource.decrement();
        listener.onDataLoadError();
    }

}
