package com.ladsoft.bakingapp.mvp.model;

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.data.repository.RecipeRepository;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource;
import com.ladsoft.bakingapp.mvp.RecipesMvp;

import java.util.List;


public class EspressoRecipesModel implements RecipesMvp.Model, RecipesMvp.Model.Callback {
    private final RecipesMvp.Model model;
    private RecipesMvp.Model.Callback listener;

    public EspressoRecipesModel(RecipeRepository recipesRepository,
                                DatabaseRecipeRepository cacheRecipesRepository,
                                DatabaseIngredientRepository cacheIngredientRepository,
                                DatabaseStepRepository cacheStepRepository) {
        this.model = new RecipesModel(recipesRepository,
                        cacheRecipesRepository,
                        cacheIngredientRepository,
                        cacheStepRepository);
    }

    @Override
    public void setListener(RecipesMvp.Model.Callback listener) {
        this.listener = listener;
        model.setListener(this);
    }

    @Override
    public void detachRecipesModelListener() {
        this.listener = null;
        model.detachRecipesModelListener();
    }

    @Override
    public void loadRecipes() {
        EspressoIdlingResource.increment();
        model.loadRecipes();
    }

    @Override
    public void onDataLoaded(List<Recipe> recipes) {
        EspressoIdlingResource.decrement();
        listener.onDataLoaded(recipes);
    }

    @Override
    public void onDataLoadError(List<Recipe> recipes) {
        EspressoIdlingResource.decrement();
        listener.onDataLoadError(recipes);
    }

    @Override
    public void onDataLoadError() {
        EspressoIdlingResource.decrement();
        listener.onDataLoadError();
    }


}
